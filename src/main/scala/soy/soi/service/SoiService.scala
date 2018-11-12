package soy.soi.service

import soy.soi.model.Soi

object SoiService {
  def addSoi(soi: Soi): Unit = {
    println(s"add soi $soi")
  }
}
