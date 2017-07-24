package controllers

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def secondPage = Action {
    Ok(views.html.secondPage())
  }

  def thirdPage(name: String) = Action {
    Ok("Hello " + name)
  }

  def fourthPage = Action {
    Result(
      header = ResponseHeader(1000000, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world"), Some("text/plain"))
    )
  }

  def fifthPage = Action {
    Redirect("/third?name=Bob", MOVED_PERMANENTLY)
  }

  def sixthPage = TODO

  def seventhPage(number: Int) = Action {
    Ok("Square of " + number + " is " + number*number)
  }



}