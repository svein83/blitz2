;THIS PROGRAMME NEEDS TO LOAD TWO SAMPLES. THE FIRST SHOULD BE ;TWO OCTAVES LOWER THAN THE SECOND. ;Ted Jones 17/12/93  ;Load instruments � 0,"ringpiano1" � 1,"ringpiano2"  � 0,10 � 0,0 � 0 �, � 0 � 0 � 0 � 2,5:݁ "A simple programme to compose fugues on 'Oh Susannah'." � 2,7:݁ "Hold right mouse button to stop."  ;Frequencies �.w 1712,1616,1524,1440,1356,1280,1208,1140,1076,1016,960,906 �.w 856,808,762,720,678,640,604,570,538,508,480,453 �.w 428,404,381,360,339,320,302,285,269,254,240,226 �.w 214,202,190,180,170,160,151,143,135,127  ;Data for "legitimate" harmonies for desired style. ;In this case very old-fashioned. ;Majors,minors,diminisheds,sevenths,sixths,minor sixths ;unisons,double notes etc. (whatever is desired) �.w 652,422,522,642,542,412,4392,3062,5602,4502,4402 �.w 4272,5712,4512,3072,4282,4382,5722,3182 �.w 12,42,102,92,52,72  ;Data for the fugue's subject ;Yes, I know the MED format would be more sensible and I have written ;a version to write MED fugues directly. ;Yes, I know a user interface to enter any tune would be nice but I'm ;as lazy as they come. I was so chuffed that the algorithm actually ;worked that the prospect of attacking other musical forms seemed infinitely ;more exciting than making a fugue utility with a hundred options. �.w 0,1,2,999,4,999,4,999,999,5,4,999,2,999,0,999,999,1,2,999,2,999,1,999,0,999,1,999 �.w 0,1,2,3,0,999,4,5,0,1,999,2  ;Data for countersubject material and permissible modulations. �.w 0,4,2,5,4,3,2,1,3,0,0,0,5,3,0,4,5,0,7,3,0,1,5,0,102,3,0,2,5,0,109,4,0,2,3,4 �.w 100,3,2,1,4,0,3,4,3,0,4,5,7,1,0,0,0,0,8,2,1,4,0,0,105,3,0,1,3,0,107,1,1,0,0,0  ;Sound structures �.chrom   adr_sound.l   ss.w   period.w � �  �.sound   _data.l   _period.w � �  ;Set fugue length (number of shortest note lengths) fl.w=512:fl1.w=fl-1  ;Dimension arrays � motifs.w(100) � voices.w(fl,4) � inst.w(fl) � chrom_scale.chrom(84) � maj_scale.w(12,84) � min_scale.w(12,84) � maj_tonics.w(12) � min_tonics.w(12) � fugue.w(fl,4) � fixed.w(fl,4) � fugue_ctype.w(fl) � modes.w(fl) � tempi.w(fl) � t.w(3),interval.w(4),s.w(4),interval2.w(4),legit_chord.w(31) � fix_pos.w(4) � maj_shifts.w(6,6),min_shifts.w(6,6)  ;Set length of fixed section motifs (opening) fix_length.w=32 � i=0 � 3:fix_pos(i)=i*fix_length:� i  ;Set out chromatic scale � i=0 � 45   � chrom_scale(i)\period   chrom_scale(i)\adr_sound=�� �(0)   chrom_scale(i)\ss=0 �  ;Read allowable chord types � i=0 � 24:� legit_chord(i):� i  ;Read the tune � i=0 � 39:� motifs(i):� i  ;Read permissible modulations � i=0 � 5:� j=0 � 5:� maj_shifts(i,j):�:� � i=0 � 5:� j=0 � 5:� min_shifts(i,j):�:�  ;Read the rest of the chromatic scale ;It is necessary to split it between two instruments ;two octaves apart  � i=46 � 71   chrom_scale(i)\period=chrom_scale(i-24)\period   chrom_scale(i)\adr_sound=�� �(1)   chrom_scale(i)\ss=1 �  ;Set out diatonic major and minor scales � i=0 � 11:wi=0   � j=0 � 71     ij=((i+j) �� 12)     � ij=0 �� ij=2 �� ij=4 �� ij=5 �� ij=7 �� ij=9 �� ij=11       maj_scale(i,wi)=j:wi+1     �� �:� � i=0 � 11:wi=0   � j=0 � 71     ij=((i+j) �� 12)     � ij=0 �� ij=2 �� ij=3 �� ij=5 �� ij=7 �� ij=8 �� ij=11       min_scale(i,wi)=j:wi+1     �� �:�  ;Establish the positions of the tonic notes for each scale � i=0 � 11   j=-1   ��     j+1   �� maj_scale(i,j)+6=maj_scale(i,j+3) �B maj_scale(i,j)+2=maj_scale(i,j+1)   maj_tonics(i)=j+4 � � i=0 � 11   j=-1   ��     j+1   �� min_scale(i,j)+3=min_scale(i,j+1)   min_tonics(i)=j+2 �  � �(1)=0  ;Clear fugue data to rests and set tempo � i=0 � fl1:� j=0 � 3:fugue(i,j)=999:�:tempi(i)=7:� � i=0 � 15   tempi(496+i)=tempi(0)+2*i � i  ;Set out modulations piece_mode.w=�(�(2)) piece_mode=0 piece_key.w=100*piece_mode+�(�(12)) modes(0)=0 ��   � i=32 � 480 � 32     � piece_mode.w=0       modes(i)=maj_shifts(modes(i-32),�(�(maj_shifts(modes(i-32),1))+2))     �"       modes(i)=min_shifts(modes(i-32),�(�(min_shifts(modes(i-32),1))+2))     ��   � �� modes(480)=0 � i=0 � 480 � 32   � piece_mode=0     r.w=maj_shifts(modes(i),0)     � r>90:q.w=100 �":q=0:��     r=(((r �� 100)+piece_key) �� 12)+q   �"     r.w=min_shifts(modes(i),0)     � r>90:q.w=100 �":q=0:��     r=(((r �� 100)+piece_key-100) �� 12)+q   ��   � j=0 � 31:modes(i+j)=r:� �  ;Insert opening modulations � piece_mode=0   sub_dom=(piece_key+5) �� 12 �"   sub_dom=((piece_key-95) �� 12)+100 �� � i=0 � 31:modes(i)=piece_key:� i � i=32 � 63:modes(i)=sub_dom:� i � i=64 � 95:modes(i)=piece_key:� i � i=96 � 127:modes(i)=sub_dom:� i  ;Write fixed sections ;Could easily be adapted to write sequences and stretto  ;Opening � i=0 � 3 � piece_mode=0   � i     � 0:spitch.w=maj_tonics(modes(i*32))+14     � 1:spitch=maj_tonics(modes(i*32))+7     � 2:spitch=maj_tonics(modes(i*32))+14     � 3:spitch=maj_tonics(modes(i*32))+7   � � �"   � i     � 0:spitch=min_tonics(modes(i*32)-100)+21     � 1:spitch=min_tonics(modes(i*32)-100)+14     � 2:spitch=min_tonics(modes(i*32)-100)+14     � 3:spitch=min_tonics(modes(i*32)-100)+7   � � ��   � j=0 � fix_length-1   ii.w=fix_length*i+j    � k=i � 3     fixed(ii,k)=1   � k   � i<3     � k=i � 3:fugue(ii,k)=999:� k   ��   key=modes(ii):mm=motifs(j)     � mm=999       fugue(ii,i)=999     �"       � key>90         key-100         fugue(ii,i)=min_scale(key,spitch+mm)       �"         fugue(ii,i)=maj_scale(key,spitch+mm)       ��     �� �:�  ;Write final cadence � i=504 � 511 � j=0 � 3 fugue(i,j)=999:fixed(i,j)=1 �:� key=modes(504) � key>=100:key-100:�� fugue(504,0)=maj_scale(key,maj_tonics(key)) fugue(504,1)=maj_scale(key,maj_tonics(key)+9) fugue(504,2)=maj_scale(key,maj_tonics(key)+11) fugue(504,3)=maj_scale(key,maj_tonics(key)+14)  ;Main composition loop ;The algorithm is very simple.For each bar and voice a random section of tune ;is selected. An attempt is made to place it so that all resulting ;harmonies belong to the "legitimate" table. This is a lot easier ;than it seems because any chord TYPE (major, minor, augmented 9th or ;what have you as opposed to inversions or positions) is completely ;characterised by a partition of twelve. A chromatic scale is a cyclic ;group of order twelve.  ;If the section of melody cannot be fitted to produce "legitimate" ;harmonies then it is transposed tonally in whatever key predominates ;(the modulations are set out in advance). If it does not fit after ;trying all seven scale transpositions it is discarded and a new one ;selected. � 2,10:݂ "NOW COMPOSING BAR        " � ii.w=0 � 508 � 4:� 22,10:݂ (ii/8)+1:� voice.w=0 � 3 � �(1)<>0:�� �:� fin:�� � fixed(ii,voice)=1:� w1:��   ��     mstart.w=�(�(10))*4:spitch.w=15+�(�(15)):s=-1     ��       s+1       k=-1         ��         k+1         key=modes(ii+k):mm=motifs(mstart+k)         � mm=999           fugue(ii+k,voice)=999         �"           � key>90             key-100             fugue(ii+k,voice)=min_scale(key,spitch+mm-s)           �"             fugue(ii+k,voice)=maj_scale(key,spitch+mm-s)           ��         ��         fi=ii+k:� chord_type         flag=0:j=-1         ��           j+1           � ctype=legit_chord(j):flag=1:��         �� j=24 �� flag=1       �� k=3 �� flag=0     �� s=6 �� flag=1   �� flag=1 w1:�:�  ;Play fugue � 2,12:݁ "NOW PLAYING BAR        " � i=0 � fl1   fi=i:� chord_type:fugue_ctype(i)=ctype � i � i=0 � fl-8   � �(1)<>0:�� �:� fin:��   � 22,12:݁ �(i/8+1)   � fugue(i,0)<999     �.w chrom_scale(fugue(i,0))\adr_sound+4,chrom_scale(fugue(i,0))\period    � chrom_scale(fugue(i,0))\ss,1   ��   � fugue(i,1)<999     �.w chrom_scale(fugue(i,1))\adr_sound+4,chrom_scale(fugue(i,1))\period     � chrom_scale(fugue(i,1))\ss,2   ��   � fugue(i,2)<999     �.w chrom_scale(fugue(i,2))\adr_sound+4,chrom_scale(fugue(i,2))\period     � chrom_scale(fugue(i,2))\ss,4   ��   � fugue(i,3)<999     �.w chrom_scale(fugue(i,3))\adr_sound+4,chrom_scale(fugue(i,3))\period     � chrom_scale(fugue(i,3))\ss,8   ��   �7 tempi(i) �  �7 200 �� fin:�7 50 �  ;Subroutine to analyse harmonies chord_type:   � q=0 � 3:t(q)=fugue(fi,q):� q   � t()   � t(0)=999:ctype=12:�:��   � m=1 � 3     � t(m)=999:t(m)=t(m-1):��   �   � q=0 � 3:t(q)=t(q) �� 12:� q   � t()   � ri=1 � 3     interval(ri-1)=t(ri)-t(ri-1)   �   interval(3)=12-interval(0)-interval(1)-interval(2)   wi=0   � m=0 � 3     interval2(m)=0     � interval(m)>0:interval2(wi)=interval(m):wi+1:��   �   � m=0 � 3:interval(m)=interval2(m):�   ctype=interval(0)+interval(1)*11+interval(2)*121+interval(3)*1331 � 