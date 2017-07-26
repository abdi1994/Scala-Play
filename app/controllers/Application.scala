package controllers

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.mvc._
import javax.inject.Inject

import models.{Item, delItem}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.concurrent.Future

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

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


  def listItems = Action { implicit request =>
    Ok(views.html.Items(Item.items, Item.createItemForm, delItem.deleteItemForm))
  }

  def createItem = Action { implicit request =>

    val formValidationResult = Item.createItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.Items(Item.items, formWithErrors, delItem.deleteItemForm))
    }, { item =>
      Item.items.append(item)
      Redirect(routes.Application.listItems())
    })
  }

  def deleteItem = Action { implicit  request =>
    val formValidationResult = delItem.deleteItemForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.Items(Item.items, Item.createItemForm, formWithErrors))
    }, { item =>
      Item.items.foreach(x => if(x.name.equals(item.name)) {Item.items -= x})
      Redirect(routes.Application.listItems())
    })
  }


  def updateItem = Action { implicit request =>

    Ok(views.html.Items(Item.items, Item.createItemForm.fill(Item.items.head), delItem.deleteItemForm))

  }




}
