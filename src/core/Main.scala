package core



object Main {
  
  def start() { 
    new Thread(new Display(new Calculator)).start()
  }
}



