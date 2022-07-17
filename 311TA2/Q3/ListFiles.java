import java.io.File;
import java.security.*;
import java.util.Arrays;

public class ListFiles {
    public static void main(String[] args) {
        try{
            System.setProperty("java.security.policy", "ListFiles.policy");
            System.setSecurityManager(new SecurityManager());
        }
        //optional. I learnt from tutorial but I found that the above parts actually did not throw exception.
        //just keep it for play safe.
        
        catch (AccessControlException ace) {
            ace.printStackTrace();
        }
        
        if(args.length != 1) {
            System.out.println("Usage: java ListFiles [File Path]");
            return;
        }
        else {
            try {
                File file = new File(args[0]);
                
                // if you are not permitted to read the file, you cannot list out the directory files/sub-directories.
                // file.exists() to check presence of files/directories because it is possible that the user would get access to a file under Java directory but not exists.
                
                if(file.exists()) {

                String[] fileList = file.list();
                
                // Arrays.stream(fileList).forEach(System.out::println); also okay                
                    for(int i = 0; i < fileList.length; i++) {
                        System.out.println(fileList[i]);
                    }
               }
                // It is also okay to add else part to handle input file name within the permission directory but does not exist.
                // but the question does not require.
            }
            //catch the exception when cannot read the details of file due to permission control.
            catch (AccessControlException ace) {
                // To generate the required output.
                System.out.println("Exception: " + ace.getMessage());
                
                //if not, I simply use
                //ace.printStackTrace();
            }
        }

    }
}