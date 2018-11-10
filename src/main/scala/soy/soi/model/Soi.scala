package soy.soi.model

case class Soi(name: String, link: String, tags: Seq[Tag])

case class Tag(name: String)
