package soy.soi.model

case class Soi(name: String, link: Link, tags: Seq[Tag])

case class Link(url: String)

case class Tag(name: String)
