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
    @StateObject private var reducer =
        ReducerViewModel<UserListScreenState,
                         GitHubUserListViewModel>(viewModel: GitHubUserListViewModel())

    var body: some View {
        let state = reducer.viewModel.stateNativeValue
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
                .listStateModifier(!state.error.isEmpty) {
                    Text("Something went wrong 🤯 \n\n" + state.error)
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
        .navigationTitle("GutHubUserFinder")
        .navigationBarTitleDisplayMode(.inline)
    }
}
