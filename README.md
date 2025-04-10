# Anime List App (WIP)
This app utilises AniList in attempt to create a simple anime list app where user can view the trending anime, greatest anime of all time, anime details and save their favorite anime.

## Table of Contents
1. [Overview](#overview)
2. [Current Features](#current-features)
3. [WIP Features](#wip-features)
4. [Architecture](#architecture)
5. [Tech Stack](#tech-stack)

## Overview
***Anime List App*** is showing anime whether they are currently trending anime or anime that are considered greatest of all time along with details of each anime when user click on it using the data fetch from ***AniList API***.

## Current Features
- ***Trending Anime List***: Displays a list of trending anime (anime cover image, name of anime, studio, genres)
- ***Anime Detail***: Displays the details of the Anime (anime name, anime description, anime banner, anime score, streaming status)

## WIP Features
- ***Greatest Anime of All time List***: Displays a list of highest rated anime of all time (anime cover image, name of anime, studio, genres)
- ***Bookmark favorite Anime***: Displays the details of the Anime (anime name, anime description, anime banner, anime score, streaming status)

## Architecture
- **Clean Architecture**:
    - **Data** layer: Repository implementations incorporating paging and data sources (Anime API with GraphQL).
    - **Domain** layer: Repository interfaces, Domain models.
    - **UI/Presentation** layer: ViewModels + Compose screens.
- **MVVM**:
    - ViewModels handle logic, composables render state.
- **Hilt**:
    - Modules providing dependencies.
- **Coroutines & Flow**:
    - Asynchronous data fetching, real-time updates.

## Tech Stack
- **GraphQL**
- **Android Paging 3**
- **Jetpack Compose**
- **Kotlin Coroutines & Flow**
- **Dagger Hilt**