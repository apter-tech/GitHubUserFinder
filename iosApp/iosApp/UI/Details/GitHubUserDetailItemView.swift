//
//  GitHubUserDetailItemView.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct GitHubUserDetailItemView: View {
    var label: String
    var value: String?

    var body: some View {
        if let value = value, !value.isEmpty {
            HStack {
                Text(label)
                    .fixedSize(horizontal: false, vertical: true)
                    .lineLimit(1)
                    .font(.system(size: 16, weight: .heavy, design: .default))
                Spacer()
                Text(value)
            }
            .padding(8)
        }
    }
}
