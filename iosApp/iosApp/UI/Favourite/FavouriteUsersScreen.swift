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
                    Text("We don't have any content, sorry 😔")
                }
                .listStateModifier(reducer.error != nil) {
                    Text("Something went wrong 🤯 \n\n" + (reducer.error ?? ""))
                }
                Spacer()
            }
            .task {
                reducer.viewModel.loadUsers()
            }
            .navigationTitle("Favourite users")
            .toolbar {
                if !state.data.isEmpty {
                    HStack {
                        Button {
                            showConfirmDialog.toggle()
                        } label: {
                            Image(systemName: "trash.fill")
                        }
                    }
                }
            }
            .confirmationDialog("Remove all user", isPresented: $showConfirmDialog, actions: {
                HStack {
                    Button("Remove all users") {
                        reducer.viewModel.deleteAllUser()
                    }
                }
            })
        }
    }
}
