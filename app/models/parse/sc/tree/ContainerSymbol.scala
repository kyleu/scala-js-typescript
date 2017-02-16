package org.scalajs.tools.tsimporter.sc.tree

import scala.collection.mutable

class ContainerSymbol(nme: Name) extends Symbol(nme) {
  val members = new mutable.ListBuffer[Symbol]

  private var _anonMemberCounter = 0
  def newAnonMemberName() = {
    _anonMemberCounter += 1
    "anon$" + _anonMemberCounter
  }

  def findClass(name: Name): Option[ClassSymbol] = members.collectFirst {
    case sym: ClassSymbol if sym.name == name => sym
  }

  def findModule(name: Name): Option[ModuleSymbol] = members.collectFirst {
    case sym: ModuleSymbol if sym.name == name => sym
  }

  def getClassOrCreate(name: Name): ClassSymbol = findClass(name).getOrElse {
    val result = new ClassSymbol(name)
    members += result
    findModule(name) foreach { companion =>
      result.companionModule = companion
      companion.companionClass = result
    }
    result
  }

  def getModuleOrCreate(name: Name): ModuleSymbol = findModule(name) getOrElse {
    val result = new ModuleSymbol(name)
    members += result
    findClass(name) foreach { companion =>
      result.companionClass = companion
      companion.companionModule = result
    }
    result
  }

  def newTypeAlias(name: Name): TypeAliasSymbol = {
    val result = new TypeAliasSymbol(name)
    members += result
    result
  }

  def newField(name: Name, readonly: Boolean = false): FieldSymbol = {
    val result = new FieldSymbol(name, readonly)
    members += result
    result
  }

  def newMethod(name: Name): MethodSymbol = {
    val result = new MethodSymbol(name)
    members += result
    result
  }

  def removeIfDuplicate(sym: MethodSymbol): Unit = {
    val isDuplicate = members.exists(s => (s ne sym) && (s == sym))
    if (isDuplicate)
      members.remove(members.indexWhere(_ eq sym))
  }
}
