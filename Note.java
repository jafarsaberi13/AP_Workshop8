public class Note {
    public static void menu() {
        System.out.println("1: Add");
        System.out.println("2: Romove");
        System.out.println("3: List");
        System.out.println("4: Export");
        System.out.println("5: Exit");
    }
    static void makeFolder() {
        Path path = Paths.get("Passwords");
        Path path1 = Paths.get("Notes");
        Path path2 = Paths.get("Export");

        try {
            Files.createDirectories(path);
            Files.createDirectories(path1);
            Files.createDirectories(path2);
        }
        catch (IOException i) {
            System.out.println("filed created folder");
            i.printStackTrace();
        }
        try {
            File file = new File("date.txt");
            file.createNewFile();
        } catch (IOException i) {
            System.out.println("date file did not created");
            i.printStackTrace();
        }
    }
    static void saveDate(HashMap<String, String> map, String name) {
        LocalDate date = LocalDate.now();
        map.put(name, "" + date);
        try {
            File file = new File("date.txt");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(map);
            o.close();
        } catch (FileNotFoundException i) {
            System.out.println("file did not find");
            i.printStackTrace();
        }
        catch (IOException ioException) {
            System.out.println("Error");
            ioException.printStackTrace();
        }
    }
    static HashMap<String, Object> getDateHashmap() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            File file = new File("date.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (HashMap<String, Object>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        return map;
    }
    static void printNameOfFile(File[] files) {
        HashMap<String, Object> dateFile = getDateHashmap();
        File notesFolders = new File("Notes");
        int i = 1;
        if (files != null) {
            for (File f: files) {
                String name = f.getName();
                int tmp = name.indexOf(".");
                System.out.println((i) + "- " + name.substring(0, tmp) + "\t" + dateFile.get(name));
                i++;
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        HashMap<String, String> date = new HashMap<>();

        final int changeNumber = 13;
        makeFolder();

        File notesFolders = new File("Notes/");

        int choice = 0;

        outter: while (true) {
            menu();
            choice = scanner.nextInt();
            if (choice == 1) { // add
                System.out.println("Enter the name of file (enter 0 for leaving)");

                String nameOfFile;
                out: while (true) {
                    File[] fileNames = notesFolders.listFiles();

                    nameOfFile = scanner.next();
                    if (nameOfFile.equals("0")) {
                        continue outter;
                    }
                    if (fileNames != null) {
                        for (File f : fileNames) {
                            if (f.isFile()) {
                                if (f.getName().equals(nameOfFile + ".txt")) {
                                    System.out.println("File exist please enter again");
                                    continue out;
                                }
                            }
                        }
                    }
                    File newFile = new File("Notes/" + nameOfFile + ".txt");
                    try {
                        if (newFile.createNewFile()) {
                            System.out.println("file created");
                            saveDate(date, nameOfFile + ".txt");
                        }
                        break out;
                    }
                    catch (IOException e) {
                        System.out.println("file did not created");
                        e.printStackTrace();
                    }
                }

                try {
                    System.out.println("Enter your notes (for ending writin enter \'#\')");
                    FileWriter myWriter = new FileWriter("Notes/" + nameOfFile + ".txt");
                    scanner.nextLine();
                    StringBuilder stringBuilder = new StringBuilder();

                    while (true) {
                        String content = scanner.nextLine();
                        if (content.equals("#")) {
                            break;
                        }
                        stringBuilder.append(content);
                        stringBuilder.append("\n");
                    }
                    String str = "" + stringBuilder;
                    for (int j = 0; j < str.length(); j++) {
                        int tmp = str.charAt(j) + changeNumber;
                        myWriter.write(tmp);
                    }

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                File[] files = notesFolders.listFiles();
                System.out.println("choose a note to delete (For leaving enter 0)");
                printNameOfFile(files);
                int t = scanner.nextInt();
                if (t == 0) {
                    continue;
                } else {
                    File file = new File("Notes/" + files[t - 1].getName());
                    if (file.delete()) {
                        System.out.println("file delete");
                    } else {
                        System.out.println("file did not deleted");
                    }
                }
            } else if (choice == 3) {
                File[] files = notesFolders.listFiles();
                printNameOfFile(files);
                System.out.println("---------------------------------------------------");
                System.out.println("Please choose the number of Note you want to see (For leaving enter 0)");
                int ch = scanner.nextInt();
                if (ch == 0) {
                    continue outter;
                }
                try {
                    FileReader fileReader = new FileReader(files[ch - 1]);
                    int j;
                    while ((j = fileReader.read()) != -1) {
                        System.out.print((char) (j - changeNumber));
                    }
                    fileReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (choice == 4) {
                File[] files = notesFolders.listFiles();
                printNameOfFile(files);
                System.out.println("---------------------------------------------------");
                System.out.println("Please choose the number of Note you want to export (For leaving enter 0)");
                int ch = scanner.nextInt();
                if (ch == 0) {
                    continue outter;
                }
                try {
                    File target = new File("Export/" + files[ch - 1].getName());
                    File src = new File("Notes/" + files[ch - 1].getName());

                    Files.copy(src.toPath(), target.toPath());
                } catch (IOException i) {
                    System.out.println("Cannot copy file");
                    i.printStackTrace();
                }
                System.out.println("The file exported");
                System.out.println("You can find it in export folder now");
            } else if (choice == 5) {
                System.out.println("GOODBYE");
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}