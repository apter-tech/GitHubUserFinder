//
//  ListStateViewModifier.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 15..
//  Copyright © 2022. orgName. All rights reserved.
//

import shared
import SwiftUI

extension View {
    func debug() -> Self {
        print(Mirror(reflecting: self).subjectType)
        return self
    }
}

struct ListStateViewModifier<ListStateContent>: ViewModifier where ListStateContent: View {
    var isNotValid: Bool
    let stateContent: () -> ListStateContent

    func body(content: Content) -> some View {
        if isNotValid {
            stateContent()
        } else {
            content
        }
    }
}

extension View {
    func listStateModifier<ListStateContent>(_ isNotValid: Bool,
                                             emptyContent: @escaping () -> ListStateContent)
    -> some View where ListStateContent: View {
        modifier(ListStateViewModifier(isNotValid: isNotValid, stateContent: emptyContent))
    }
}

struct EmptyStateView_Previews: PreviewProvider {
    static var previews: some View {
        Label("Content", systemImage: "heart")
            .listStateModifier(true) {
                Text(MR.strings().empty_view_title.localize())
            }
    }
}

struct ErrorStateView_Previews: PreviewProvider {
    static var previews: some View {
        Label("Content", systemImage: "heart")
            .listStateModifier(true) {
                Text("Something went wrong 🤯")
            }
    }
}
