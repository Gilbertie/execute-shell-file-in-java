import java.io.*;
import java.util.function.Consumer;

public class SimpleTest {

    public static final String WINDOWS_PREFIX = "windows";
    public static final String MAC_PREFIX = "max";
    public static final String UNIX_PREFIX = "nix";
    public static final String path_bash = "C:/Program Files/Git/git-bash.exe";
    public static final String shellScriptsDirectory = "./shellscripts";
    public static final String shellScriptFileName = "./sh1.sh";

    public static void main(String[] args) throws IOException, InterruptedException {
        // detect operating system
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println(String.format("The operating system is %s", osName)); // use log later

        // build ProcessBuilder
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows(osName)) {
            builder.command(path_bash, "-c", shellScriptFileName);
        } else if (isMac(osName)) {
            builder.command("sh", "-c", shellScriptFileName);
        } else if (isUnix(osName)) {
            builder.command("bash", "-c", shellScriptFileName);
        } else {
            System.out.println("Unsupported OS."); // use log later
            return;
        }
        builder.directory(new File(shellScriptsDirectory));

        // run ProcessBuilder
        Process process = builder.start();
//        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
//        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        assert exitCode == 0;
    }

//    private static class StreamGobbler implements Runnable {
//        private InputStream inputStream;
//        private Consumer<String> consumer;
//
//        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
//            this.inputStream = inputStream;
//            this.consumer = consumer;
//        }
//
//        @Override
//        public void run() {
//            new BufferedReader(new InputStreamReader(inputStream)).lines()
//                    .forEach(consumer);
//        }
//    }

    private static boolean isWindows(String osName) {
        return osName.startsWith(WINDOWS_PREFIX);
    }

    private static boolean isMac(String osName) {
        return osName.startsWith(MAC_PREFIX);
    }

    private static boolean isUnix(String osName) {
        return osName.startsWith(UNIX_PREFIX);
    }
}
