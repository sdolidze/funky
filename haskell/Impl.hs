module Impl where

head' :: [a] -> a
head' (x:_) = x

tail' :: [a] -> [a]
tail' (_:xs) = xs

init' :: [a] -> [a]
init' [x] = []
init' (x:xs) = x : init' xs

last' :: [a] -> a
last' [x] = x
last' (_:xs) = last' xs

null' :: [a] -> Bool
null' [] = True
null' (_:_) = False

length' :: [a] -> Int
length' [] = 0
length' (_:xs) = 1 + length' xs

-- A.K.A. !!
nth :: Int -> [a] -> a
nth 0 (x:_) = x
nth n (x:xs) = nth (n-1) xs

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' _ v [] = v
foldr' f v (x:xs) = f x (foldr' f v xs)

-- I still have hard time understanding this
-- may overflow the stack, because of lazy evaluation
foldl' :: (b -> a -> b) -> b -> [a] -> b
foldl' _ v [] = v
foldl' f v (x:xs) = foldl' f (f v x) xs

map' :: (a -> b) -> [a] -> [b]
map' f = foldr (\x xs -> f x : xs) []

sum' :: [Int] -> Int
sum' = foldr' (+) 0

product' :: [Int] -> Int
product' = foldr' (*) 1