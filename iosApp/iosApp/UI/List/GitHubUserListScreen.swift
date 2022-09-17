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
        VStack(alignment: .center) {
            SearchBar { query in
                reducer.viewModel.searchUser(userName: query)
            }
            Spacer()
            if reducer.viewModel.stateNativeValue.isLoading {
                ProgressView()
            } else {
                List {
                    ForEach(reducer.viewModel.stateNativeValue.data, id: \.id) { item in
                        NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                            GitHubUserRow(item: item)
                        }
                    }
                    if !reducer.viewModel.stateNativeValue.isFetchingFinished {
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
                .listStateModifier(reducer.viewModel.stateNativeValue.data.isEmpty) {
                    Text("We don't have any content, sorry 😔")
                }
                .listStateModifier(!reducer.viewModel.stateNativeValue.error.isEmpty) {
                    Text("Something went wrong 🤯 \n\n" + reducer.viewModel.stateNativeValue.error)
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
