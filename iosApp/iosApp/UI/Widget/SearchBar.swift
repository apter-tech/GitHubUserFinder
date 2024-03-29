//
//  SearchBar.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import shared
import SwiftUI

struct SearchBar: View {
    @State private var searchQuery = ""
    var action: (String) -> Void

    var body: some View {
        HStack {
            TextField(MR.strings().search_app_bar_title.localize(),
                      text: $searchQuery)
                .modifier(ClearButtonModifier(text: $searchQuery))
                .padding(10)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .autocapitalization(.none)
                .disableAutocorrection(true)
                .padding(.horizontal, 10)
                .onSubmit { action(searchQuery) }
                .submitLabel(.search)
        }
    }
}
