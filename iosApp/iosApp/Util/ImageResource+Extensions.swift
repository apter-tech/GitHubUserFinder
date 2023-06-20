//
//  ImageResource+Extensions.swift
//  iosApp
//
//  Created by Imre Kaszab on 2023. 06. 16..
//  Copyright © 2023. orgName. All rights reserved.
//

import shared
import SwiftUI
import UIKit

extension ImageResource {
    func asUIImage() -> UIImage {
        self.toUIImage() ?? UIImage()
    }

    func asImage() -> Image {
        Image(uiImage: self.asUIImage())
    }
}
