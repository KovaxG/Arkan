@ECHO OFF
SETLOCAL EnableDelayedExpansion
for /f "tokens=2 delims=," %%a in ('tasklist /nh /fi "imagename eq cmd.exe" /fo csv') do (
set MYPID=%%a
set music=""
GOTO ready
)
:ready

echo Tutorial:
echo So... This is a Dating Simulator Game. I know what you're thinking: "U wut m8?". Yes indeed it's a simulator where you can get a girlfriend.
echo In this game YOU have to write everything. I mean no choises like 1 2 or 3, but clear sentences: "Go home".
echo The game is designed in a way that you don't trick it. USE COMMON SENSE. If your sentences don't make sense the AI probably won't understand it or worse misunderstand it. Also don't expect everything to be implemented. If it seems important and it's not implemented yet, then write me a line and I'll look into it.
echo Also don't finish the sentences with . ? or exclamation mark, only if you leave a space between the last word and the sign.
echo My E-mail is : andorgalfi@yahoo.com but you can also find me on facebook. So if the girls just don't want to do your math homework write me a line and I'll implement it.
echo So lets get started.

:strresp
echo First test: type start to start the game and exit to exit.
SET /P response=
cls
if "%response%"=="start" (
GOTO start
) else (
if "%response%"=="exit" (
	GOTO exit
	) else (
		echo So you couldn't even do THIS right... This game will be haaard
		GOTO strresp
	)
)

:start

echo It is late spring. You are in your first highschool year. 
echo Until now you have been focusing on your ... (strength, agility, intelligence, charisma)
:focusresp
SET /P answer=
cls
if "%answer%"=="strength" (
SET /A my_STR=5
SET /A my_AGY=3
SET /A my_INT=2
SET /A my_CHA=3
) else (
if "%answer%"=="agility" (
SET /A my_STR=3
SET /A my_AGY=5
SET /A my_INT=3
SET /A my_CHA=2
) else (

if "%answer%"=="intelligence" (
SET /A my_STR=2
SET /A my_AGY=3
SET /A my_INT=5
SET /A my_CHA=3
) else (

if "%answer%"=="charisma" (
SET /A my_STR=3
SET /A my_AGY=2
SET /A my_INT=3
SET /A my_CHA=5
) else (
echo No, you couldn't have focused on THAT, try again...
GOTO focusresp
)
)
)
)

echo But now that you made it into highschool and survived the first semester you KNOW that it is time to get a girlfriend.
echo In your class you consider 3 candidates:
echo Clarity, the quiet girl who you rarely see talking to anyone. In breaks she puts on her headphones and listenes to music, noone knows what kind though. But still she has an unknown charm. She has brown eyes and dark, short hair.
SET /A Cl_STR=2
SET /A Cl_AGY=4
SET /A Cl_INT=2
SET /A Cl_CHA=2
SET /A Cl_ATR=0
SET /A Cl_RAP=0
echo Avery, the class's brain and rep. She is quite popular throughout the whole school. She has a gorgeous face and the long red hair strangely gives away her mischievous, hidden personality.
SET /A Av_STR=2
SET /A Av_AGY=3
SET /A Av_INT=5
SET /A Av_CHA=3
SET /A Av_ATR=0
SET /A Av_RAP=0
echo Evelynn, yeah... the rebel. She always craves for fun no matter what the cost, so she doesn't really care about other people's emotions. Her presence simply can't be ignored, she simply projects herself to everyone in front of her and on top of that she has a perfect 10 body with nice, long, curvy hair and deep blue eyes.
SET /A Ev_STR=3
SET /A Ev_AGY=3
SET /A Ev_INT=2
SET /A Ev_CHA=6
SET /A Ev_ATR=0
SET /A Ev_RAP=0

SET /A semester=2
SET /A day=16

:highschool
SET /A go=0
SET /A nothing=0
SET /A talk=0
SET /A train=0
SET person=""

echo You are at your highschool in the %semester%. semester on day %day%. The first three hours have passed and it's lunch break. What do you do?
:doresp
SET /P doresp=
cls
for %%a in (%doresp%) do (
if /i "%%a"=="go" (
	SET /A go+=1
)
if /i "%%a"=="eat" (
	SET /A nothing+=1
)
if /i "%%a"=="nothing" (
	SET /A nothing+=1
)
if /i "%%a"=="approach" (
	SET /A talk+=1
)
if /i "%%a"=="talk" (
	SET /A talk+=1
)
if /i "%%a"=="train" (
	SET /A train+=1
)
if /i "%%a"=="strength" (
	SET totrain=%%a
)
if /i "%%a"=="agility" (
	SET totrain=%%a
)
if /i "%%a"=="intelligence" (
	SET totrain=%%a
)
if /i "%%a"=="charisma" (
	SET totrain=%%a
)
if /i "%%a"=="Clarity" (
	SET person=%%a
)
if /i "%%a"=="Avery" (
	SET person=%%a
)
if /i "%%a"=="Evelynn" (
	SET person=%%a
)
if /i "%%a"=="people" (
	SET totrain=charisma
	SET /A train+=1
)
if /i "%%a"=="classmates" (
	SET totrain=charisma
	SET /A train+=1
)
if /i "%%a"=="guys" (
	SET /A nothing+=1
)
if /i "%%a"=="teacher" (
	SET person=%%a
)
if /i "%%a"=="home" (
	SET destination=%%a
)
if /i "%%a"=="out" (
	SET /A nothing+=1
)
if /i "%%a"=="read" (
	SET /A nothing+=1
)
)

if %train% gtr 0 (	
	echo Do you really want to spend the semester training?
	:training
	SET /P trainanswer=	
	if /i "!trainanswer!"=="yes" (
		echo You spend the entire semester both at home and school to train your %totrain%.
		SET /A semester+=1
		SET /A day=16
		GOTO highschool
	) else (
		if /i "!trainanswer!"=="no" (
			echo You change your mind.
			GOTO highschool
		) else (
			echo What?
			GOTO training
		)
	)
) else (
	if %talk% gtr 0 (
		if "%person%"=="teacher" (
			echo You talk with the last class's teacher.
			SET /A nothing+=1
			GOTO nothing
		) else (
			if /i "%person%"=="Clarity" (
				GOTO conv
			) else (
				if /i "%person%"=="Avery" (
				GOTO conv
				) else (
					if /i "%person%"=="Evelynn" (
					GOTO conv
					) else (
						GOTO nothing
					)
				)
			)
		)
		GOTO nothing
	) else (
		if %go% gtr 0 (
			if "%destination%"=="home" (
				Echo Do you really want to skip the rest of the day?
				:skip
				SET /P skipresponse=
				if /i "!skipresponse!"=="yes" (
					echo You go home and spend the rest of your day there.
					SET /A day+=1
					GOTO highschool
				) else (
					if /i "!skipresponse!"=="no" (
						echo You stay at school.
						GOTO highschool
					) else (
						echo What?
						GOTO skip
					)
				)
			)
			) else (
				:nothing
				if %nothing% gtr 0 (					
					Echo You spend you day doing nothing special. Chatting here or there, eating your lunch, but nothing interesting happens.
					SET /A day+=1
					GOTO highschool
				) else (
					Echo Sorry, I couldn't get that.
					GOTO highschool
				)
			)
		)
	)
:conv
if /i "%person%"=="Clarity" (
	if NOT "%music%"=="Clarity" (
		SET /p pid=<file
		echo !pid!
		del file
		taskkill /FI "pid eq !pid!"
		START Cl.bat %MYPID%
		SET music="Clarity"
	)
) else (
	if /i "%person%"=="Avery" (
		if NOT "%music%"=="Avery" (
			SET /p pid=<file
			echo !pid!
			del file
			taskkill /FI "pid eq !pid!"
			START Av.bat %MYPID%
			SET music="Avery"
		)
	) else (
		if /i "%person%"=="Evelynn" (
			if NOT "%music%"=="Evelynn" (
				SET /p pid=<file	
				echo !pid!		
				del file
				taskkill /FI "pid eq !pid!"
				START Ev.bat %MYPID%
				SET music="Evelynn"
			)
		)
	)
)
cls
echo You approach %person% and begin to talk to her. What do you say? (or do - start with I)
:reconv
SET /A comp=0
SET /A tease=0
SET /A question=0
SET /A touch=0
SET /A kiss=0
SET /A look=0
SET /A me=0
SET /A hello=0
SET /A bye=0
SET topic=""
SET target=""
:talkresp
SET /P talkresp=
for %%a in (%talkresp%) do (
	if /i "%%a"=="I" (
		SET /A me+=1
	)
	if !me! gtr 0 (
		if /i "%%a"=="kiss" (
			SET /A kiss+=1
			SET action=kiss
		)
		if !kiss! gtr 0 (
			if /i "%%a"=="her" (
				SET target=mouth
			)
			if /i "%%a"=="hand" (
				SET target=hand
			)
			if /i "%%a"=="cheek" (
				SET target=cheek
			)
			if /i "%%a"=="forhead" (
				SET target=forhead
			)
			if /i "%%a"=="neck" (
				SET target=neck
			)
		)
		if /i "%%a"=="look" (
			SET /A look+=1
			SET action=look
		)
		if !look! gtr 0 (
			if /i "%%a"=="eyes" (
				SET target=eyes
			)
			if /i "%%a"=="her" (
				SET target=body
			)
		)
		if /i "%%a"=="hold" (
			SET action=hold
			SET /A touch+=1
		)
		if /i "%%a"=="touch" (
			SET action=touch
			SET /A touch+=1
		)
		if /i "%%a"=="pat" (
			SET action=pat
			SET /A touch+=1
		)
		if /i "%%a"=="caress" (
			SET action=caress
			SET /A touch+=1
		)
		if /i "%%a"=="take" (
			SET action=take
			SET /A touch+=1
		)
		if !touch! gtr 0 (			
			if /i "%%a"=="hand" (
				SET target=hand
			)
			if /i "%%a"=="back" (
				SET target=back
			)
			if /i "%%a"=="face" (
				SET target=face
			)
		)
	)
	if "%%a"=="?" (
		SET /A question+=1
	)
	if /i "%%a"=="great" (
		SET /A comp+=1
	)
	if /i "%%a"=="beautiful" (
		SET /A comp+=1
	)
	if /i "%%a"=="brave" (
		SET /A comp+=1
	)
	if /i "%%a"=="smart" (
		SET /A comp+=1
	)
	if /i "%%a"=="best" (
		SET /A comp+=1
	)
	if /i "%%a"=="like" (
		SET /A comp+=1
	)
	if /i "%%a"=="awsome" (
		SET /A comp+=1
	)
	if /i "%%a"=="well" (
		SET /A comp+=1
	)
	if /i "%%a"=="pretty" (
		SET /A comp+=1
	)
	if /i "%%a"=="charming" (
		SET /A comp+=1
	)
	if /i "%%a"=="breathtaking" (
		SET /A comp+=1
	)
	if /i "%%a"=="good" (
		SET /A comp+=1
	)
	if /i "%%a"=="wonderful" (
		SET /A comp+=1
	)
	if /i "%%a"=="cute" (
		SET /A comp+=1
	)
	if /i "%%a"=="inspiring" (
		SET /A comp+=1
	)
	if /i "%%a"=="fun" (
		SET /A comp+=1
	)
	if /i "%%a"=="magnificent" (
		SET /A comp+=1
	)
	if /i "%%a"=="gorgeous" (
		SET /A comp+=1
	)
	if /i "%%a"=="patient" (
		SET /A comp+=1
	)
	if /i "%%a"=="nice" (
		SET /A comp+=1
	)
	if /i "%%a"=="toughtful" (
		SET /A comp+=1
	)
	if /i "%%a"=="special" (
		SET /A comp+=1
	)
	if /i "%%a"=="too" (
		SET /A tease+=1
		SET /A comp-=1
	)
	if /i "%%a"=="bad" (
		SET /A tease+=1
	)
	if /i "%%a"=="ugly" (
		SET /A tease+=1
	)
	if /i "%%a"=="impatient" (
		SET /A tease+=1
	)
	if /i "%%a"=="boring" (
		SET /A tease+=1
	)
	if /i "%%a"=="hate" (
		SET /A tease+=1
	)
	if /i "%%a"=="clumsy" (
		SET /A tease+=1
	)
	if /i "%%a"=="simple" (
		SET /A tease+=1
	)
	if /i "%%a"=="hobby" (
		SET /A question+=1
		SET topic=hobby
	)
	if /i "%%a"=="hobbies" (
		SET /A question+=1
		SET topic=hobby
	)
	if /i "%%a"=="interests" (
		SET /A question+=1
		SET topic=hobby
	)
	if /i "%%a"=="time" (
		SET /A question+=1
		SET topic=time
	)
	if /i "%%a"=="name" (
		SET /A question+=1
		SET topic=name
	)	
	if /i "%%a"=="what" (
		SET /A question+=1
	)
	if /i "%%a"=="hi" (
		SET /A hello+=1
	)
	if /i "%%a"=="hello" (
		SET /A hello+=1
	)
	if /i "%%a"=="bye" (
		SET /A bye+=1
	)
	if /i "%%a"=="goodbye" (
		SET /A bye+=1
	)
	if /i "%%a"=="cya" (
		SET /A bye+=1
	)
	if /i "%%a"=="number" (
		SET /A question+=1
		SET topic=number
	)
		
)
if /i "%person%"=="Clarity" (
	GOTO Clresp
) else (
	if /i "%person%"=="Avery" (
	GOTO Avresp
	) else (
		if /i "%person%"=="Evelynn" (
		GOTO Evresp
		)
	)
)

:Clresp
SET /A modif=my_CHA-Cl_CHA
if %hello% gtr 0 (
	if %Cl_ATR% geq 20 (
		echo Oh, hi. 
	) else (
		if %Cl_RAP% geq 20 (
			echo Oh, hi. 
		) else (
			echo Umm... hi. 
		)
	)
) else (
	if %question% gtr 0 (
		if "%topic%"=="name" (
			echo My name is Clarity. But you should have known this by now.
			SET /A Cl_RAP+=10
		)
		if "%topic%"=="hobby" (
			echo Umm... Let's see. I like listening to music. Playing games like board games or card games. Long walks...
			SET /A Cl_RAP+=20
		)
		if "%topic%"=="time" (
			echo It is 11.
			SET /A Cl_RAP+=10
		)
		if "%topic%"=="number" (
			if %Cl_ATR% geq 40 (
				echo Yeah, okay. It's 036124486.
				SET /A Cl_RAP+=20
			) else (
				if %Cl_RAP% geq 60 (
					echo Yeah, sure. It's 036124486.
					SET /A Cl_RAP+=20
				) else (
					echo No, sorry. I don't know you well enough.
					SET /A Cl_RAP-=20
				)
			)
		)
	) else (
		if %comp% gtr 0 (
			if "%tease%" gtr 0 (
				if %Cl_ATR% geq 20 (
					echo You're so mean!
					SET /A Cl_ATR-=20
				) else (
					echo You're so mean! Don't talk to me! Bye.
					SET /A Cl_ATR=0
					SET /A day+=1
					GOTO highschool
				)
			) else (
			echo Really? Thank you, you're so kind.
			SET /A Cl_ATR+=20+modif
			)
	) else (
		if %kiss% gtr 0 (
			if "%target%"=="hand" (
				if %Cl_ATR% geq 40 (
					SET /A Cl_ATR+=20+modif
					echo You %action% %person%'s %target%. She blushes, smiles and silently giggles. 
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her hand away.
					if %Cl_ATR% geq 20 (
						SET /A Cl_ATR-=20
					) else (
						echo Leave me!
						SET /A Cl_ATR=0
						SET /A day+=1
						GOTO highschool
					)
				)
			)
			if "%target%"=="cheek" (
				if %Cl_ATR% geq 50 (
					SET /A Cl_ATR+=20+modif
					SET /A Cl_RAP+=20+modif
					echo You %action% %person%'s %target%. She blushes and smiles. 
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					if %Cl_ATR% geq 20 (
						SET /A Cl_ATR-=20
						SET /A Cl_RAP=0
					) else (
						echo Leave me!
						SET /A Cl_ATR=0
						SET /A Cl_RAP=0
						SET /A day+=1
						GOTO highschool
					)
				)
			)
			if "%target%"=="mouth" (
				if %Cl_ATR% geq 80 (
					if %Cl_RAP% geq 80 (
						SET /A Cl_ATR+=20+modif
						SET /A Cl_RAP+=20+modif
						echo You %action% %person%. Her eyes are closed. You clearly feel that she likes it. 
						echo I love you. Don't ever leave me.
						GOTO victory
					) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo What do you think you're doing? You trying to trick me into kissing you. You are just a player. You would dump me after two days.
					SET /A Cl_ATR=-20
					SET /A Cl_RAP=-20
					SET /A day+=1
					GOTO highschool
					)
				) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo What do you think you're doing? I tought we were just friends. Let's stay that way.
					SET /A Cl_ATR=0
					SET /A day+=1
					GOTO highschool
				)
			)
			if "%target%"=="neck" (
				echo You try to %action% %person%'s %target%, but she pulls her head away.
				echo Don't do that!
				if %Cl_ATR% geq 20 (
					SET /A Cl_ATR-=20
					SET /A Cl_RAP=0
				) else (
					echo Leave me!
					SET /A Cl_ATR=0
					SET /A Cl_RAP=0
					SET /A day+=1
					GOTO highschool
				)
			)
			if "%target%"=="forhead" (
				if %Cl_ATR% geq 20 (
					if %Cl_RAP% geq 40 (
						echo You %action% %person%'s %target%. She smiles and feels more confortable around you like all her stress melted away. 
						SET /A Cl_RAP+=20+modif
					) else (
						echo You try to %action% %person%'s %target%, but she pulls her head away.
						if %Cl_RAP% geq 20 (
							SET /A Cl_RAP-=20
							SET /A Cl_ATR-=20
						) else (
							SET /A Cl_ATR=0
							SET /A Cl_ATR-=20
						)							
					)
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					if %Cl_RAP% geq 20 (
						SET /A Cl_RAP-=20
						SET /A Cl_ATR=0
					) else (
						SET /A Cl_RAP=0
						SET /A Cl_ATR=0
					)
				)
			)
	) else (
		if %look% gtr 0 (
			if "%target%"=="eyes" (
				echo You gaze deeply into her eyes. She looks back into your's for a while, but then she looks away.
				SET /A Cl_ATR+=10+modif
			)
			if "%target%"=="body" (
				echo You measure her up, looking at all of her features. She notices what you are doing.
				echo Hey, stop staring!
				SET /A Cl_ATR-=10
			)
	) else (
		if %touch% gtr 0 (
		    if %CL_ATR% geq 40 (
				if %CL_RAP% geq 40 (
					echo You %action% %person%'s %target%. She likes it.
					SET /A Cl_ATR+=10+modif
					SET /A Cl_RAP+=10+modif
				) else (
					echo You try to %action% %person%'s %target%, but she pushes your hand away.
					SET /A Cl_ATR-=10
					SET /A Cl_RAP-=10
				)
			)
		) else (
		if %bye% gtr 0 (
			echo Bye.
			SET /A day+=1
			GOTO highschool
		) else (
			echo Hmmm?
			GOTO reconv
)
)	
)
)
)	
)
) 

GOTO reconv

:Avresp
SET /A modif=my_CHA-Av_CHA
if %hello% gtr 0 (
	if %Av_ATR% geq 20 (
		echo Hi.
	) else (
		if %Av_RAP% geq 20 (
			echo Hi.
		) else (
			echo Hello. 
		)
	)
) else (
	if %question% gtr 0 (
		if "%topic%"=="name" (
			echo We are at the %semester%. semester and you still don't know my name... My name is Avery.
			SET /A Av_RAP+=10
		)
		if "%topic%"=="hobby" (
			echo I like reading, learning, figuring out misteries about the world. I have a lot of secrets but I would like to keep them that way. I also like to travel.
			SET /A Av_RAP+=20
		)
		if "%topic%"=="time" (
			echo It is 11.
			SET /A Av_RAP+=10
		)
		if "%topic%"=="number" (
			if %Av_ATR% geq 30 (
				echo Yeah, okay. It's 036217895.
				SET /A Av_RAP+=20
			) else (
				if %Av_RAP% geq 40 (
					echo Yeah, sure. It's 036217895.
					SET /A Av_RAP+=20
				) else (
					echo No, sorry. I don't know you well enough.
					SET /A Av_RAP-=20
				)
			)
		)
	) else (
		if %comp% gtr 0 (
			if %comp% gtr %tease% (
				if %tease% gtr 0 (
					echo You're funny!
					SET /A Av_ATR+=10+modif
				) else (
					echo I already knew that...
				)
			) else (
				echo Please don't insult me. It hurts.
				SET /A Av_ATR-=10
				SET /A Av_RAP-=10
			)
	) else (
		if %tease% gtr 0 (
			echo Please don't insult me. It hurts.
			SET /A Av_ATR-=10
			SET /A Av_RAP-=10
	) else (
		if %kiss% gtr 0 (
			if "%target%"=="hand" (
				if %Av_ATR% geq 50 (
					SET /A Av_ATR+=15+modif
					echo You %action% %person%'s %target%. She blushes and raises an eyebrow.
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her hand away.
					SET /A Av_ATR-=20
					echo Don't do that... it's embarrassing.
				)
			)
			if "%target%"=="cheek" (
				if %Av_ATR% geq 60 (
					SET /A Av_ATR+=15+modif
					SET /A Av_RAP+=15+modif
					echo You %action% %person%'s %target%. She blushes and smiles. 
					echo You're so sweet.
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					echo Don't do that... What are you thinking?!
					SET /A Av_ATR-=25
					SET /A Av_RAP-=25
				)
			)
			if "%target%"=="mouth" (
				if %Av_ATR% geq 100 (
					if %Av_RAP% geq 100 (
						SET /A Av_ATR+=40+modif
						SET /A Av_RAP+=40+modif
						echo You %action% %person%. Her eyes are closed. You clearly feel that she is into it. 
						echo I love you. Always be by my side!
						GOTO victory
					) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo What do you think you're doing? You filthy player. You do this to all the girls don't you? You make small talk then try to kiss them right off the bat. I won't be a name that you can simply cross out from your list. I'm worth more than that.
					SET /A Av_ATR=-20
					SET /A Av_RAP=-20
					SET /A day+=1
					GOTO highschool
					)
				) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo What do you think you're doing? We are just friends. I don't have any other feelings toward you.
					SET /A Av_ATR=0
					SET /A day+=1
					GOTO highschool
				)
			)
			if "%target%"=="neck" (
				if %Av_ATR% geq 60 (
					echo You %action% %person%'s %target%. She slips away a silent moan. She likes this very much.
					echo My weak spot. How did you know?!
					SET /A Av_ATR+=20
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					echo Don't do that you pervert!
					SET /A Av_ATR-=20
				)
			)
			if "%target%"=="forhead" (
				if %Av_ATR% geq 40 (
					if %Av_RAP% geq 60 (
						echo You %action% %person%'s %target%. She smiles and feels more confortable around you like all her stress melted away. 
						SET /A Av_RAP+=20+modif
					) else (
						echo You try to %action% %person%'s %target%, but she pulls her head away.
						echo I'm not a child anymore. Don't treat me like one!
						if %Av_RAP% geq 20 (
							SET /A Av_RAP-=20
							SET /A Av_ATR-=20
						) else (
							SET /A Av_RAP=0
							SET /A Av_ATR-=20
						)							
					)
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					echo I'm not a child anymore. Don't treat me like one!
					if %Av_RAP% geq 20 (
						SET /A Av_RAP-=20
						SET /A Av_ATR=0
					) else (
						SET /A Av_RAP=0
						SET /A Av_ATR=0
					)
				)
			)
	) else (
		if %look% gtr 0 (
			if "%target%"=="eyes" (
				echo You gaze deeply into her eyes. She keeps looking back into your's without a flinch until both of you blink at the same time.
				SET /A Av_ATR+=5+modif
			)
			if "%target%"=="body" (
				echo You measure her up, looking at all of her features. She notices what you are doing.
				echo Inappropriate!
				SET /A Av_ATR-=10
			)
	) else (
		if %touch% gtr 0 (
		    if %Av_ATR% geq 40 (
				if %Av_RAP% geq 40 (
					echo You %action% %person%'s %target%. She likes it.
					SET /A Av_ATR+=10+modif
					SET /A Av_RAP+=10+modif
				) else (
					echo You try to %action% %person%'s %target%, but she pushes your hand away.
					SET /A Av_ATR-=10
					SET /A Av_RAP-=10
				)
			)
	) else (
		if %bye% gtr 0 (
			echo GoodBye.
			SET /A day+=1
			GOTO highschool
	) else (
		echo Can you please repeat that?
		GOTO reconv
)
)
)	
)
)
)	
)
) 

GOTO reconv

:Evresp
SET /A modif=my_CHA-Ev_CHA
if %hello% gtr 0 (
	if %Ev_ATR% geq 80 (
		echo Hi, dear.
	) else (
		if %Ev_RAP% geq 60 (
			echo Hi, dear.
		) else (
			echo Heya. 
		)
	)
) else (
	if %question% gtr 0 (
		if "%topic%"=="name" (
			echo Now here's a name you got to remember ... Evelynn, stick it deep into your little head.
			SET /A Ev_RAP+=5
		)
		if "%topic%"=="hobby" (
			echo My interests? Hmmm. There are alot. I like most of all to party, dance, go wild. You know I'm not the usual type who likes to sit at home and read or do somekind of sport... I like to live my life at it's fullest.
			SET /A Ev_RAP+=10
		)
		if "%topic%"=="time" (
			echo The time is right now.
			SET /A Ev_RAP+=5
		)
		if "%topic%"=="number" (
			if %Ev_ATR% geq 60 (
				echo Yeah, sure, honey. It's 036185647.
				SET /A Ev_RAP+=10
			) else (
				if %Ev_RAP% geq 60 (
					echo Yeah, sure, honey. It's 036185647.
					SET /A Ev_RAP+=10
				) else (
					echo What? You really tought that I would just simply give you my number? You have to work alot harder for that, loser.
					SET /A Ev_RAP-=20
				)
			)
		)
	) else (
		if %comp% gtr 0 (
			if %comp% equ %tease% (
				echo Heey, stop teasing me... You're so baad. 
				SET /A Ev_ATR+=10
			) else (
			if %comp% gtr %tease% (
				echo This is what every guy tells me. Boring....
				SET /A Ev_ATR-=10
			) else (
				echo This is just mean...
				SET /A Ev_ATR-=10
				SET /A Ev_RAP-=10
			)
			)
	) else (
		if %tease% gtr 0 (
			echo This is just mean...
			SET /A Ev_ATR-=10
			SET /A Ev_RAP-=10
	) else (
		if %kiss% gtr 0 (
			if "%target%"=="hand" (				
				echo You try to %action% %person%'s %target%, but she pulls her hand away.
				echo I'm not looking for Prince Charming to come and kiss my hand, thank you. And bye.
				SET /A day+=1
				GOTO highschool
			)
			if "%target%"=="cheek" (
				if %Ev_ATR% geq 20 (
					SET /A Ev_ATR+=10+modif
					SET /A Ev_RAP+=10+modif
					echo You %action% %person%'s %target%. She doesn't bat an eye. She is probably used to getting kissed.
					echo Don't think I'll jump into your arms...
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					echo You're miserable, go sit down kid.
					SET /A Ev_ATR-=30
					SET /A Ev_RAP-=30
					SET /A day+=1
					GOTO highschool
				)
			)
			if "%target%"=="mouth" (
				if %Ev_ATR% geq 60 (
					if %Ev_RAP% geq 30 (
						SET /A Av_ATR+=20+modif
						SET /A Av_RAP+=20+modif
						echo You %action% %person%. Her eyes are closed. You clearly feel that she is into it. After a certain point she takes over the lead. You enjoy this too much even.
						echo I think I'll tag along with you for the time being. But don't get too cocky.
						GOTO victory
					) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo I don't know a single thing about you. Usually I don't care, but you're just creepy.
					SET /A Ev_ATR=-20
					SET /A Ev_RAP=-20
					SET /A day+=1
					GOTO highschool
					)
				) else (
					echo You try to %action% %person%, but she pulls her head away.
					echo Gosh. I'm not even close to being into you. What were you thinking? Get lost, loser.
					SET /A Ev_ATR=-10
					SET /A Ev_RAP=-10
					SET /A day+=1
					GOTO highschool
				)
			)
			if "%target%"=="neck" (
				if %Ev_ATR% geq 40 (
					echo You %action% %person%'s %target%. She grabs your hair and keeps your head where it is so that you can keep doing what you're doing.
					echo Mmmmm. Keep going. I like it there.
					SET /A Ev_ATR+=10
				) else (
					echo You try to %action% %person%'s %target%, but she pulls her head away.
					echo Sit down Sparky, we are not there yet.
					SET /A Ev_ATR-=20
				)
			)
			if "%target%"=="forhead" (
				echo You try to %action% %person%'s %target%, but she pulls her head away.
				echo I'm not a child and I don't need any guy to kiss my forhead, thank you. Now get lost!
				SET /A day+=1
				GOTO highschool
			)
	) else (
		if %look% gtr 0 (
			if "%target%"=="eyes" (
				echo You gaze deeply into her eyes. She looks back into your's without a flinch. You keep staring into eachother's eyes for a while until you feel that her gaze is getting deeper into you. You suddenly break eye contact.
				SET /A Ev_ATR-=5
			)
			if "%target%"=="body" (
				echo You measure her up, looking at all of her features. She notices what you are doing.
				echo You like what you see? Right? Just as I thought.
				SET /A Ev_ATR+=10+modif
			)
	) else (
		if %touch% gtr 0 (
		    if %EV_ATR% geq 20 (
				if %EV_RAP% geq 10 (
					echo You %action% %person%'s %target%. She likes it.
					SET /A Cl_ATR+=15+modif
				) else (
					echo You try to %action% %person%'s %target%, but she pushes your hand away.
					echo No touchy, touchy.
					SET /A Cl_ATR-=10
					SET /A Cl_RAP-=10
				)
			)
		) else (
		if %bye% gtr 0 (
			echo Cya.
			SET /A day+=1
			GOTO highschool
		) else (
			echo What?
			GOTO reconv
)
)	
)
)
)	
)
)
) 

GOTO reconv

:victory
echo CONGRATULATIONS YOU JUST GOT YOURSELF A GIRLFRIEND
echo This is all it is in this game for now, but there are other new feature to come like:
echo	-new places to visit
echo	-more girlfriends AT ONCE
echo	-random events
echo	-secret challenges
echo But until then try charming the other girls too (Gotta catch'em all)

SET /P var=Press any key to quit

:exit

ENDLOCAL
ECHO ON