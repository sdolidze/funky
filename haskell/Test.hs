module Test where

import Test.Hspec
import Control.Exception (evaluate)
import Impl

main = hspec $ do
  describe "Impl" $ do
    it "list" $ do
      head [1,2,3] `shouldBe` 1
      tail [1,2,3] `shouldBe` [2,3]
      init [1,2,3] `shouldBe` [1,2]
      last [1,2,3] `shouldBe` 3
      null [] `shouldBe` True
      null [1] `shouldBe` False
      length [1,2] `shouldBe` 2
      length [] `shouldBe` 0
      nth 0 [1,2]  `shouldBe` 1 
      nth 1 [1,2]  `shouldBe` 2
      map' (+1) [1,2] `shouldBe` [2,3]
      sum' [1,2] `shouldBe` 3
      product' [1,2] `shouldBe` 2