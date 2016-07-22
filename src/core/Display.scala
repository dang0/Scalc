package core

import scala.swing.MainFrame
import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.GridPanel
import scala.swing.Dialog
import scala.swing.Button
import scala.swing.Label
import scala.swing.MenuBar
import scala.swing.Swing
import scala.swing.Orientation
import scala.swing.Alignment
import java.awt.Dimension
import java.awt.Font
import scala.swing.Separator
import scala.swing.Menu
import scala.swing.event.Key
import scala.swing.MenuItem
import scala.swing.Action


class Display(calc: Calculator) extends MainFrame with Runnable {
  val WINDOW_W = 225
  val WINDOW_H = 300

  val mPanel = new BoxPanel(Orientation.Vertical) with Controller {
    model = calc
    border = Swing.EmptyBorder(5)
    listenTo(keys)
    def kode() {
      Dialog.showMessage(this, "you did the kode\ntruly amazing\nwow\nreally cool", "//SECRET//", Dialog.Message.Error, null)
    }
  }
  
  val textPanel = new BorderPanel {
    focusable = false
    val textField = new Label("0") {
      focusable = false
      font = new Font(Font.MONOSPACED, Font.BOLD, 20)
      //horizontalTextPosition = Alignment.Right
      horizontalAlignment = Alignment.Right
      preferredSize = new Dimension(205, 35)}
    layout(textField) = BorderPanel.Position.East
    def text = textField.text
    def text_=(s: String) = textField.text_=(s)
    def text_+=(s: String) = textField.text_=(text + s)
  }
  
  val buttonPanel = new GridPanel(5,4) {
    focusable = false
    vGap = 5
    hGap = 5
  }
  
  val buttonList = List(
	   "bk", "C", "+/-", "rt", 
       "7", "8", "9", "*",
       "4", "5", "6", "/",
       "1", "2", "3", "+",
       "0", ".", "=", "-"
  )
  
  for (i <- buttonList) {
    var b = new Button(i) { preferredSize = new Dimension(50,35) }
    b.focusable = false
    mPanel.listenTo(b)
    buttonPanel.contents += b
  }

  val menu = new MenuBar {
    focusable = false
    contents += (
      new Menu("File"){ mnemonic = Key.F; contents += (new MenuItem(new Action("Close") { def apply() { closeOperation }}))},
      new Menu("Help"){ mnemonic = Key.H; contents += (new MenuItem(new Action("You're on your own") { def apply() { Dialog.showMessage(mPanel, "this space intentionally left blank", "no help", Dialog.Message.Warning, null) }}))}
    )
  }
  menuBar = menu
  
  mPanel.contents += (textPanel, new Separator{preferredSize = new Dimension(2,2)}, buttonPanel)
  contents = mPanel

  title = "Scalculator"
  resizable = false
  size = new Dimension(WINDOW_W, WINDOW_H)
  centerOnScreen
  mPanel.requestFocusInWindow
  pack

  open

  def run() {
    while (true) {
      mPanel.requestFocusInWindow
      
      textPanel.text = (if(calc.negFlag && calc.displayNum.charAt(0) != '-') "-" else "") + calc.displayNum + (if(calc.decimalFlag) "." else "")
      Thread.sleep(20)
    }
  }
  
  
}