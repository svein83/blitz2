' a blitzmax tool to untokenise old bb2 files

Global func:String[65536]

path$="/Users/simon/blitz2/husker3.bb2"

libs$="libs.txt"
tokens$="tokens.txt"
opcodes$="opcodes.txt"

' asm opcodes

cmdnum=60+$8000
lines$=LoadText(opcodes)
For line$=EachIn lines.Split("~n")
	p=line.find("=")
	pr=line.find(",")
	op$=line[p+1..pr]
'	Print cmdnum+op
	func[cmdnum]=op
	cmdnum:+1
Next

' standard bum6 libs

' Blitz library 'blitzlibs:basic/audiolib.obj' (#116)
' [322]  ###[186,3 $00003A03]  3,g$+"Motor.IFF" 


'Blitz library 'blitzlibs:basic/gameiolib.obj' (#190)  JoyX (Port)
'    ojoy.w=joy.w:joy=###[188,10 $0000BC0A](###[177,2 $0000B102]($4c)-###[177,2 $0000B102]($4d)+###[223,2 $0000DF02](1),-1,1)


' Blitz library 'blitzlibs:basic/memacclib.obj' (#180) $B4
'  Poke [.Type] Address,Data
'  Peek [.Type](Address)
'  Peeks$ (Address,Length)
'  Call Address
'   ##[$DA01].w $dff032,1477

' da-b4 = 26
' 8e->da
lines$=LoadText(libs)
libnum=0
cmdnum=0
For line$=EachIn lines.Split("~n")
	If libnum=0
		p=line.Find("(#")
		libnum=line[p+2..].toInt()		
		cmdnum=(libnum+256)*128
		Print line +"->"+ libnum +"->"+ Hex(cmdnum)	'(libnum+257)/2)'4+19+16+38+180
'		libnum:+257
'		libnum:+38
'		cmdnum=libnum*256
	Else
		line=line.Trim()
		If line="" 
			libnum=0
			cmdnum=0
		Else		
			cmdnum:+1
			p=line.find(" ")
			If p=-1 p=line.length
'			Print "$"+Hex(cmdnum)[4..]+":"+line[..p]	'+"---"+line[p..]
			If func[cmdnum]
				Print "Name Collison"
			EndIf
			func[cmdnum]=line[..p]
		EndIf
	EndIf
Next

' tokens from source

lines$=LoadText(tokens)
For token$=EachIn lines.Split("~n")
	token=token.Trim()
	If token[..1]=";" Continue
	p=token.find("=")
	If p<>-1
		lhs$=token[..p]
		rhs$=token[p+1..]		
		If rhs[..1]="$"
			cmd=HexToDecimal(rhs[1..])
'			Print cmd+"="+lhs			
			func[cmd]=lhs
		ElseIf rhs[..1]="~q"
			If lhs[..3]="tok"
				cmd=Int(lhs[3..])+$8000
				rhs=rhs[1..rhs.length-1]
				For name$=EachIn rhs.Split(",")					
'					Print cmd +"<-"+name
					func[cmd]=name
					cmd:+1
				Next
			EndIf
		Else
			Print lhs + " : " +rhs
		EndIf
	EndIf
Next

bank:TBank=LoadBank(path)
Print bank.Size()

src$=""

For i=0 Until bank.size()-1

b=bank.PeekByte(i)
b1=bank.PeekByte(i+1)

If b=0 
	src:+"~r~n"
EndIf

If b>31 And b<127 
	src:+Chr(b)
EndIf

If b<32 And b>0
	Print b
EndIf

If b>127
	cmd=(b Shl 8)|(b1&255)
	i:+1
	
'	If b>128 Print Hex(cmd)[4..]
	token$=func[cmd]	
'	If token="" token="###["+b+","+b1+" $"+Hex(cmd)[4..]+"]"
	If token="" token="##[$"+Hex(cmd)[4..]+"]"
	src:+token	
EndIf

Next

'Print src

SaveText src,"test.txt"

'src$=LoadString(path)
'Print src.length

Function HexToDecimal(h$)
	Local t2$=Upper$(Trim$(h$))
	Local d=0
	For Local z=1 To Len(t2$)
		Local i=Instr("0123456789ABCDEF",Mid$(t2$,z,1))
		If i>0 Then d=d*16+i-1
	Next
	Return d
End Function

Function ScanBB2(path$)
	For Local dir$=EachIn LoadDir(path)	
		If dir[0]="." Continue
		Local path2$=path+"/"+dir
		Select FileType(path2)
			Case FILETYPE_DIR
				ScanBB2 path2
			Case FILETYPE_FILE
				If dir.ToLower().EndsWith(".bb2")
				
				EndIf
		End Select
	Next
End Function
