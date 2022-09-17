//
//  GitHubUserListScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct GitHubUserListScreen: View {
    @StateObject private var observableViewModel = GitHubUserListViewModelImpl()

    var body: some View {
        VStack(alignment: .center) {
            SearchBar { query in
                observableViewModel.viewModel.searchUser(userName: query)
            }
            Spacer()
            if observableViewModel.isLoading {
                ProgressView()
            } else {
                List {
                    ForEach(observableViewModel.items, id: \.id) { item in
                        NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                            GitHubUserRow(item: item)
                        }
                    }
                    if !observableViewModel.isFetchingFinished {
                        HStack {
                            Spacer()
                            ProgressView()
                            Spacer()
                        }
                        .onAppear {
                            observableViewModel.viewModel.requestNextPage()
                        }
                    }
                }
                .listStateModifier(observableViewModel.items.isEmpty) {
                    Text("We don't have any content, sorry 😔")
                }
                .listStateModifier(!observableViewModel.error.isEmpty) {
                    Text("Something went wrong 🤯 \n\n" + observableViewModel.error)
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
