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
    
}