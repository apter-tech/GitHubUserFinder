//
//  GitHubUserRow.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared

struct GitHubUserRow: View {
    var item: GitHubUser

    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            AsyncImage(url: URL(string: item.avatarUrl)!) { image in
                image.resizable().scaledToFill()
            } placeholder: { Color.gray }
            .frame(width: 60, height: 60)
            .cornerRadius(16)
            VStack(alignment: .leading, spacing: 4) {
                Text(item.login)
                    .fixedSize(horizontal: false, vertical: true)
                    .lineLimit(1)
                    .font(.system(size: 16, weight: .heavy, design: .default))
            }
        }
        .padding(4)
    }
}
