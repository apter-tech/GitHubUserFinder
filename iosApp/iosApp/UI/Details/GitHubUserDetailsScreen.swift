//
//  GitHubUserDetailsScreen.swift
//  iosApp
//
//  Created by Imre Kaszab on 2022. 04. 10..
//  Copyright © 2022. orgName. All rights reserved.
//

import SwiftUI
import shared

struct GitHubUserDetailsScreen: View {
    @StateObject private var reducer = ReducerViewModel<UserDetailsScreenState, GitHubUserDetailsViewModel>()
    var userName: String

    var body: some View {
        if let state = reducer.state {
            ScrollView {
                if let userDetails = state.userDetails {
                    VStack {
                        if userDetails.name != userName {
                            Text(userDetails.name)
                                .font(.title)
                                .foregroundColor(.primary)
                        }
                        AsyncImage(url: URL(string: userDetails.avatarUrl)!) { image in
                            image.resizable().scaledToFill()
                        } placeholder: { Color.gray
                        }
                        .frame(width: 100, height: 100)
                        .cornerRadius(16)
                        GitHubUserDetailBioView(bio: userDetails.bio)
                        GitHubUserDetailItemViews(userDetails: userDetails)
                    }
                }
            }
            .navigationBarTitle(Text(userName))
            .toolbar {
                HStack {
                    Button {
                        if state.userDetails?.favourite == true {
                            reducer.viewModel.deleteUser()
                        } else {
                            reducer.viewModel.saveUser()
                        }
                    } label: {
                        if state.userDetails?.favourite == true {
                            MR.images().ic_star_fill.asImage()
                        } else {
                            MR.images().ic_star.asImage()
                        }
                    }
                }
            }
            .onAppear {
                reducer.viewModel.refreshUserDetails(userName: userName)
            }
            .onDisappear {
                reducer.deactivate()
            }
        }
    }
}
