import org.apache.wicket.MarkupContainer

def visitComponent(c, level, f) {
    f(c, level)
    
    if (c instanceof MarkupContainer && c.size() > 0) {
        (0..c.size()-1).each{
          visitComponent(c.get(it),level+1,f)
        }
    }
}

visitComponent(page, 0, {c, level -> 
    level.times {print "  "}
    println "${c.id} [${c.class.simpleName}]"
  }
)
