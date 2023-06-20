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
                        Text(MR.strings().empty_view_title.localize())
                    }
                    .listStateModifier(reducer.error != nil) {
                        Text(MR.strings().error_view_title.localize(input: reducer.error ?? "" ))
                    }
                }
                Spacer()
            }
            .toolbar {
                HStack {
                    NavigationLink(destination: FavouriteUsersScreen()) {
                        MR.images().ic_star_fill.asImage()
                    }
                }
            }
            .task {
                reducer.viewModel.loadUsers()
            }
            .navigationTitle(MR.strings().app_name.localize())
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}
