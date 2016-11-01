package controllers

import javax.inject._

import play.api.i18n.{Lang, Messages, MessagesApi}
import play.api.mvc._

@Singleton
class HomeController @Inject()(messagesApi: MessagesApi) extends Controller {

  def index = Action {

    val texts = List(
      messageTest("test-key.lang-name"),
      messageTest("nothing"),
      messageTest("test-key.defined-at-only-ja"),
      messageTest("test-key.defined-at-only-zh"),
      messageTest("test-key.defined-at-only-en")
    ).mkString("\n\n")
    
    
    Ok(views.html.index(texts))
  }
  
  def messageTest(messageName: String): String = {
    val languageCodes = List("en", "en-us", "ja-jp", "zh-cn")
    languageCodes.map(langCode => {
      val messages = Messages(Lang(langCode), messagesApi)
      val isDefined = messages.isDefinedAt(messageName)
      val translatedMessage: Option[String] = messages.translate(messageName, List.empty[String])
      val message = messages(messageName)
      val text = s"${messageName} [${langCode}] = ${message}, isDefined = ${isDefined}, translatedMessage = ${translatedMessage}"
      println(text)
      text
    }).mkString("\n")
  }

}
