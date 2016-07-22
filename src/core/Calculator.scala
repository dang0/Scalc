package core

import scala.math.BigDecimal

class Calculator {
  var displayNum = "0" 
  var numX = BigDecimal("0")
  var numY = BigDecimal("0")
  var operation = ""
  var newNumFlag = true
  var decimalFlag = false
  var negFlag = false
  var lastCmd = ""
    
  def clear() {
    displayNum = "0"
    numX = BigDecimal("0")
    numY = BigDecimal("0")
    operation = ""
    newNumFlag = true
    decimalFlag = false
    negFlag = false
    lastCmd = ""
  }

  def doIt(e: String) {
    e match {
      case "C" => clear
      case "bk" if decimalFlag =>
      case "bk" if !newNumFlag => displayNum = (if (displayNum.toString.length() == 1) "0" else displayNum.toString.substring(displayNum.toString.length() - 1))
      case "+" | "-" | "/" | "*" if lastCmd == "+" || lastCmd == "-" || lastCmd == "/" || lastCmd == "*" => operation = e
      case "+" | "-" | "/" | "*" =>
        if (operation != "") evaluate; storeNum; operation = e; newNumFlag = true
      case "rt" => doSquareRoot
      case "+/-" => if (newNumFlag) { displayNum = "0"; newNumFlag = false; negFlag = false }; negFlag = !negFlag
      case "=" if operation != "" => evaluate
      case "." if !decimalFlag =>
        if (newNumFlag) { displayNum = "0"; newNumFlag = false; negFlag = false }; if (!displayNum.toString.contains(".")) decimalFlag = true
      case e if e.charAt(0).isDigit =>
        if (newNumFlag) { displayNum = ""; newNumFlag = false; negFlag = false }; displayNum = BigDecimal(displayNum + (if (decimalFlag) "." else "") + e).toString
      case e => //println("not found: " + e)
    }
    lastCmd = e
    if (decimalFlag && lastCmd != "." && lastCmd != "+/-") decimalFlag = false
  }
  
  def doOperation(): BigDecimal = {
    operation match {
      case "+" => doAddition
      case "-" => doSubtraction
      case "*" => doMultiplication
      case "/" => doDivision
    }
  }
  
  def doAddition(): BigDecimal = {
    numX + numY
  }
  
  def doSubtraction(): BigDecimal = {
    numX - numY
  }
  
  def doMultiplication(): BigDecimal = {
    numX * numY
  }
  
  def doDivision(): BigDecimal = {
    numX / numY
  }
  
  def doSquareRoot() {
    if(negFlag) displayNum = "NaN"
    else displayNum = math.sqrt(displayNum.toDouble).toString
    if(displayNum.contains(".")) displayNum = BigDecimal(displayNum.toString.substring(0,math.min(15,displayNum.toString.length())).reverse.dropWhile(_ == '0').reverse).toString
    newNumFlag = true
    negFlag = false
  }
  
  def storeNum() {
    numX = BigDecimal((if(negFlag && displayNum.charAt(0) != '-') "-" else "") + displayNum)
  }
  
  def evaluate() {
    numY = BigDecimal((if(negFlag && displayNum.charAt(0) != '-') "-" else "") + displayNum)
    displayNum = doOperation.toString
    if(displayNum.contains(".")) displayNum = BigDecimal(displayNum.toString.substring(0,math.min(15,displayNum.toString.length())).reverse.dropWhile(_ == '0').reverse).toString
    newNumFlag = true
    operation = ""
  }
}