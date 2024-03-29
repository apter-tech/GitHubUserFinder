//
//  ClearButtonModifier.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 22..
//  Copyright © 2022. orgName. All rights reserved.
//

import shared
import SwiftUI

struct ClearButtonModifier: ViewModifier {
    @Binding var text: String

    public func body(content: Content) -> some View {
        ZStack(alignment: .trailing) {
            content
            if !text.isEmpty {
                Button {
                    self.text = ""
                }
            label: {
                MR.images().ic_arrow_left
                    .asImage()
                    .foregroundColor(Color(UIColor.opaqueSeparator))
                }
            }
        }
    }
}
