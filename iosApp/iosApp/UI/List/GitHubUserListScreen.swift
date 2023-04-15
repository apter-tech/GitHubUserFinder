//
//  GitHubUserListScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared

struct GitHubUserListScreen: View {
    @StateObject private var reducer = ReducerViewModel<UserListScreenState, GitHubUserListViewModel>()

    var body: some View {
        if let state = reducer.state {
            VStack(alignment: .center) {
                SearchBar { query in
                    reducer.viewModel.searchUser(userName: query)
                }
                Spacer()
                if state.isLoading {
                    ProgressView()
                } else {
                    List {
                        ForEach(state.data, id: \.id) { item in
                            NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                                GitHubUserRow(item: item)
                            }
                        }
                        if !state.isFetchingFinished {
                            HStack {
                                Spacer()
                                ProgressView()
                                Spacer()
                            }
                            .onAppear {
                                reducer.viewModel.requestNextPage()
                            }
                        }
                    }
                    .listStateModifier(state.data.isEmpty) {
                        Text("We don't have any content, sorry 😔")
                    }
                    .listStateModifier(reducer.error != nil) {
                        Text("Something went wrong 🤯 \n\n" + (reducer.error ?? ""))
                    }
                }
                Spacer()
            }
            .toolbar {
                HStack {
                    NavigationLink(destination: FavouriteUsersScreen()) {
                        Image(systemName: "star.circle")
                    }
                }
            }
            .task {
                reducer.viewModel.loadUsers()
            }
            .navigationTitle("GutHubUserFinder")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}
