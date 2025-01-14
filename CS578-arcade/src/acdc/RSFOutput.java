package acdc;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * This class has one method which creates an RSF file.
 * <p>
 * The string representation of the output is of the format:
 * <p>
 * contain parent_node node
 */
public class RSFOutput implements OutputHandler {

  public void writeOutput(String outputName, DefaultMutableTreeNode root) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(outputName)));
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    Node ncurr, nj, ni, np;
    DefaultMutableTreeNode curr, i, j, pi;

    Enumeration allNodes = root.breadthFirstEnumeration();

    // Avoid output for the root node
    i = (DefaultMutableTreeNode) allNodes.nextElement();

    String prev = null;

    while (allNodes.hasMoreElements()) {
      i = (DefaultMutableTreeNode) allNodes.nextElement();

      ni = (Node) i.getUserObject();

      pi = (DefaultMutableTreeNode) i.getParent();

      np = (Node) pi.getUserObject();

      String cleanNpName = np.getName();
      if (np.getName().startsWith("\"") && !np.getName().endsWith("\"")) {
        cleanNpName = np.getName().substring(1, np.getName().length());
      }

      if (pi != root) {
        if (prev != null && cleanNpName.equals(prev) == false) { // better format
	        out.println("");
        }
        out.println("contain " + cleanNpName + " " + ni.getName());
      }

      prev = cleanNpName;
    }
    out.close();
  }
}