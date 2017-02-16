package models.parse.sc.tree

class PackageSymbol(nme: Name) extends ContainerSymbol(nme) {
  override def toString = s"package $name"

  def findPackage(name: Name): Option[PackageSymbol] = {
    members.collectFirst {
      case sym: PackageSymbol if sym.name == name => sym
    }
  }

  def getPackageOrCreate(name: Name): PackageSymbol = {
    findPackage(name) getOrElse {
      val result = new PackageSymbol(name)
      members += result
      result
    }
  }
}
