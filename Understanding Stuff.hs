class Config {
	size :: [Double] -- Size of the map
	x_obst :: Col Double -- Location of obstacle
	x_goal :: Col Double -- Location of goal
}

class Model extends Config {
	p :: Double
	q :: Double
	det :: Double
	Ts :: Double
	fun :: String 
	visualizefun :: String
	U :: Cell Double
	Udeltax :: [Double]
	rew_step :: Double
	rew_obst :: Double
	rew_goal :: Double
	X :: Cell Double Double
	Xflat :: Martix Double
	Xterminal :: [Double]
	minx :: Col Double
	maxx :: Col Double
	minu :: Col Double
	maxu :: Col Double
}

class GridView {
	figh :: Double
	N :: [Double]
	cells :: Martix Double
    img_goal :: Double
	arrows :: [Martix Double]
	img_robot :: Double
}

class VisualConfig {
	model :: Model
	x :: Double
	gview :: GridView
    Q :: [Martix Double]
    h :: Martix Double
}

cfg :: Config
model :: Model
xplus :: ColMatrix
rplus :: Double
terminal :: Bool
viscfg :: VisualConfig


-- The first argument will be fitted between the 2 others
sat :: Double -> Double -> Double -> Double
sat x xmin xmax
    | x < xmin = xmin
    | x > xmax = xmax
    | otherwise = x

gridnav_problem :: String -> Config -> Model
gridnav_mdp :: Model -> ColMatrix -> Double -> (ColMatrix, Double, Bool)
gridnav_visualize :: VisualConfig -> GridView


