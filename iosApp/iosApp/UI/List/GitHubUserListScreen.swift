//
//  GitHubUserListScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI

struct GitHubUserListScreen: View {
    @StateObject private var viewModel = GitHubUserListViewModel()

    var body: some View {
        VStack(alignment: .center) {
            SearchBar { query in
                Task {
                    await viewModel.searchUser(userName: query)
                }
            }
            Spacer()
            if viewModel.isLoading {
                ProgressView()
            } else {
                List {
                    ForEach(viewModel.items, id: \.id) { item in
                        NavigationLink(destination: GitHubUserDetailsScreen(userName: item.login)) {
                            GitHubUserRow(item: item)
                        }
                    }
                    if viewModel.isFetchingFinished == false {
                        HStack {
                            Spacer()
                            ProgressView()
                            Spacer()
                        }
                        .onAppear {
                            Task {
                                await viewModel.fetchNextPage()
                            }
                        }
                    }
                }
                .emptyState(viewModel.items.isEmpty) {
                    Text("We don't have any content, sorry 😔")
                }
            }
            Spacer()
        }
    }
}
