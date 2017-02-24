{-
 - Google Hashcode 2017
 -}

data Video = Video { id :: Int
                   , size :: Int
                   } deriving (Show, Eq)

data Cache = Cache { id :: Int
                   , size :: Int
                   , videos :: [Video]
                   } deriving (Show, Eq)

data Request = Request { video :: Video
                       , demand :: Int
                       } deriving (Show, Eq)

data DataCenter = DataCenter { videos :: [Video]
                             } deriving (Show, Eq)                       

data EndPoint = EndPoint { id :: Int
                         , requests :: [Request]
                         , connections :: [(Cache, Int)]
                         , latency :: Int
                         } deriving (Show, Eq)

main :: IO ()
main = do
    content <- readFile "test.txt"
    return ()