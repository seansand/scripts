
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.FlavorEvent;

/**
 * Provides methods for working with the system clipboard.
 * <p>
 * Only provides support for text. 
 * 
 * @author Peter De Bruycker
 */
public class ClipboardMethodsNew {

    private Clipboard clipboard
    
    public static void main(String[] args) throws Exception 
    { 
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() { 
            @Override 
            public void flavorsChanged(FlavorEvent e) 
            { 
                println("changed!!! " + e.getSource() + " " + e.toString()); 
            } 
        }); 
        Thread.sleep(100000L); 
    }
    
    
    public void setClipboard(String text) {
        if(clipboard == null) {
            Toolkit tk = Toolkit.getDefaultToolkit()
            clipboard = tk.systemClipboard
        }
        
        def ss = new StringSelection(text)
        clipboard.setContents(ss, ss)
    }
    
    public String getClipboard() {
        if(clipboard == null) {
            Toolkit tk = Toolkit.getDefaultToolkit()
            clipboard = tk.systemClipboard
        }
        
        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            return clipboard.getData(DataFlavor.stringFlavor)
        }
        
        return null
    }
}