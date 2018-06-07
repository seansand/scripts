char rot13(s) {
    char c = s
    switch(c) {
        case 'A'..'M': case 'a'..'m': return c+13
        case 'N'..'Z': case 'n'..'z': return c-13
        default : return c
    }
}
String.metaClass.rot13 = {
    delegate.collect(this.&rot13).join()
}

from = '!"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~'
to = 'PQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~!"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNO'
String.metaClass.rot47 = {
    delegate.collect{ int found = from.indexOf(it); found < 0 ? it : to[found] }.join()
}

println(args[0].rot13())