package core

import scala.swing.Reactor
import scala.swing.event.ButtonClicked
import scala.swing.event.KeyTyped
import scala.swing.event.KeyPressed
import scala.swing.event.Key


trait Controller extends Reactor {
	var model: Calculator = null
	var konamiKode = 0
	
	reactions += {
	  case e: ButtonClicked => input(e.source.text); konamiKode = 0
	  case e: KeyTyped if e.char.isDigit => input(e.char.toString); konamiKode = 0
	  case KeyPressed(_,Key.Up,_,_) if konamiKode == 0 || konamiKode == 1 => konamiKode += 1
	  case KeyPressed(_,Key.Down,_,_) if konamiKode == 2 || konamiKode == 3 => konamiKode += 1
	  case KeyPressed(_,Key.Left,_,_) if konamiKode == 4 || konamiKode == 6 => konamiKode += 1
	  case KeyPressed(_,Key.Right,_,_) if konamiKode == 5 || konamiKode == 7 => konamiKode += 1
	  case KeyPressed(_,Key.B,_,_) if konamiKode == 8 => konamiKode += 1
	  case KeyPressed(_,Key.A,_,_) if konamiKode == 9 => konamiKode += 1
	  case KeyPressed(_,Key.Enter,_,_) if konamiKode == 10 => kode; konamiKode = 0
	  case e: KeyPressed =>  konamiKode = 0
	  case e =>
	}
	
	def input(e: String) {
      if ( model.displayNum.toString.length() < 15 || !e.charAt(0).isDigit || model.newNumFlag) model.doIt(e)
    }
	
	def kode()
}