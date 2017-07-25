package controllers

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._

class Application extends Controller {

  def index : Action[AnyContent] = Action {
    Ok(views.html.index("Your new application is ready.")).withSession("connected" -> "user@gmail.com")
  }

  def index2 : Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index("Your new application is ready.")).withSession(request.session + ("test" -> "yes"))
  }

  def secondPage : Action[AnyContent] = Action {
    Ok(views.html.secondPage()).discardingCookies(DiscardingCookie("CookieExercise"))
  }

  //Includes cookies
  def thirdPage(name: String) : Any = Action {
    Ok("Hello " + name).withCookies(Cookie("CookieExercise", "value"))
  }

  def fourthPage : Action[AnyContent] = Action {
    Result(
      header = ResponseHeader(helpers.Helper.responseValue, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello world"), Some("text/plain"))
    )
  }

  def fifthPage : Action[AnyContent] = Action {
    Redirect("/third?name=Bob", MOVED_PERMANENTLY)
  }

  def sixthPage : Action[AnyContent] = TODO

  def seventhPage(number: Int) : Action[AnyContent] = Action {
    Ok("Square of " + number + " is " + number*number)
  }



}
