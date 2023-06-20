//
//  FavouriteUsersScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 09. 17..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared

struct FavouriteUsersScreen: View {
    @StateObject private var reducer = ReducerViewModel<FavouriteUsersScreenState, FavouriteUsersViewModel>()
    @State private var showConfirmDialog = false

    var body: some View {
        if let state = reducer.state {
            VStack(alignment: .center) {
                Spacer()
                List {
                    ForEach(state.data, id: \.id) { item in
                        NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                            GitHubUserRow(item: item)
                        }
                    }
                }
                .listStateModifier(state.data.isEmpty) {
                    Text(MR.strings().empty_view_title.localize())
                }
                .listStateModifier(reducer.error != nil) {
                    Text(MR.strings().error_view_title.localize(input: reducer.error ?? "" ))
                }
                Spacer()
            }
            .task {
                reducer.viewModel.loadUsers()
            }
            .navigationTitle(MR.strings().favourite_screen_title.localize())
            .toolbar {
                if !state.data.isEmpty {
                    HStack {
                        Button {
                            showConfirmDialog.toggle()
                        } label: {
                            MR.images().ic_trash.asImage()
                        }
                    }
                }
            }
            .confirmationDialog(MR.strings().remove_all_user_dialog_title.localize(),
                                isPresented: $showConfirmDialog, actions: {
                HStack {
                    Button(MR.strings().remove_all_user_dialog_title.localize()) {
                        reducer.viewModel.deleteAllUser()
                    }
                }
            })
        }
    }
}
