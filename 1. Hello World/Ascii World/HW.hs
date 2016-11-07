{-
 - Arkan 1. Kihivas
 - Tema: Hello World!
 - Nev: Ascii World!
 - Szerzo: Kovacs Gyorgy
 - Datum: 2016.10.08
 -}

main :: IO ()
main = do 
    putStrLn "+--------------------------------------------------------------+"
    putStrLn "| |   |  +--  |    |    |--|   \\        / |--|  +--+  |    |-\\ |"
    putStrLn "| |---|  |--  |    |    |  |    \\  /\\  /  |  |  +--+  |    |  ||"
    putStrLn "| |   |  +--  +--  +--  |--|     \\/  \\/   |--|  |\\    +--  |-/ |"
    putStrLn "+--------------------------------------------------------------+"
    putStrLn ""
    putStrLn "by Kovacs Gyorgy"
    _ <- getLine -- Ha nem olvasok be egy uj sort, akkor egybol lezarodik a program.
    return ()
    
-- putStrLn :: String -> IO () 
-- Kiir valamit a kepernyore

-- getLine :: IO String
-- visszaterit egy stringet, amit a felhasznalo ir be
-- a string olvasasnak akkor van vege, ha a felhasznalo
-- megnyomja az Entert.

-- return () :: IO ()
-- Komplikalt. Az a lenyeg, h a tipus az IO (), s a 
-- a main-nek is a tipusa IO (), de mivel a getLine
-- tipusa IO String, nem lehet az a main utolso sorja.
-- Ezert kell rakni egy return () et, mert az nem csinal
-- semmit, viszont a main az IO () marad a vegen.
-- Ugyanakkor a putStrLn "bla" -nak a tipusa IO (),
-- tehat vegzodhet a main egy puttal, azaz ugyanugy
-- lehetne az utolso sor :
-- putStrLn "semmi"