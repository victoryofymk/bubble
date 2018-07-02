package taobao;//package per.son.com.taobao;

//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//
//import org.jdesktop.jdic.browser.BrowserEngineManager;
//import org.jdesktop.jdic.browser.IBrowserEngine;
//import org.jdesktop.jdic.browser.IWebBrowser;
//import org.jdesktop.jdic.browser.WebBrowser;
//import org.jdesktop.jdic.browser.WebBrowserEvent;
//import org.jdesktop.jdic.browser.WebBrowserListener;
//
//public class BrowserTest {
//	public static IWebBrowser browser;
//
//	public static void main(String[] args) throws MalformedURLException {
//		BrowserEngineManager bem = BrowserEngineManager.instance();
//		bem.setActiveEngine(BrowserEngineManager.IE);
//		// IBrowserEngine be = bem.getActiveEngine();
//		/*
//		 * browser = be.getWebBrowser();//new WebBrowser();
//		 * browser.addWebBrowserListener(new WebBrowserListener() { public void
//		 * downloadStarted(WebBrowserEvent event) { System.out.println("27"); }
//		 * public void downloadCompleted(WebBrowserEvent event) {
//		 * System.out.println("30"); } public void
//		 * downloadProgress(WebBrowserEvent event) { System.out.println("33"); }
//		 * public void downloadError(WebBrowserEvent event) {
//		 * System.out.println("36"); } public void
//		 * documentCompleted(WebBrowserEvent event) { System.out.println("39");
//		 * browser.executeScript("alert('文档下载完毕！')"); } public void
//		 * titleChange(WebBrowserEvent event) { System.out.println("43"); }
//		 * public void statusTextChange(WebBrowserEvent event) {
//		 * System.out.println("46"); } public void windowClose(WebBrowserEvent
//		 * webBrowserEvent) { System.out.println("49"); } public void
//		 * initializationCompleted(WebBrowserEvent arg0) {
//		 * System.out.println("52"); } });
//		 */
//		WebBrowser browser = new WebBrowser();
//		browser.setURL(new URL("https://www.tmall.com"));
//		//
//		JFrame frame = new JFrame("Browser Test");
//
//		frame.getContentPane().add(browser);
//		frame.pack();
//
//		frame.setSize(900, 500);
//		frame.setLocation((int) (100 * Math.random()), (int) (100 * Math.random()));
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//
//	}
//
//}
