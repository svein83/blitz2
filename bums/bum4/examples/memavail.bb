; ; memory available demo ;    #ANY=0   #CHIP=2   #FAST=4   #CLEAR=$10000    ;NA only used when allocating memory   #LARGEST=$20000   #TOTAL=$80000    ݂ " fast largest:",��(#FAST|#LARGEST)   ݂ " fast maximum:",��(#FAST|#TOTAL)   ݂ "   fast avail:",��(#FAST)   ݂ ""   ݂ " chip largest:",��(#CHIP|#LARGEST)   ݂ " chip maximum:",��(#CHIP|#TOTAL)   ݂ "   chip avail:",��(#CHIP)   ݂ ""   ݂ "  all largest:",��(#ANY|#LARGEST)   ݂ "  all maximum:",��(#ANY|#TOTAL)   ݂ "    all avail:",��(#ANY)  ; mousewait ;(needed if running from Blitz2 and not CLI) 