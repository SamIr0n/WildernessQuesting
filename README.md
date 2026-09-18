# Wilderness Questing

A data-driven quest framework for Minecraft mods and modpacks. Quest content is defined through JSON, while the mod supplies the quest engine, user interface, persistence, synchronization, and extension points.

> **Project status:** Planning and specification

## Project Goals

- Provide a fully data-driven quest system using JSON files.
- Allow quests to be supplied through datapacks or mod resources.
- Use namespaced identifiers to prevent conflicts between packs and mods.
- Validate quest data and report clear, actionable errors.
- Reload quest definitions without restarting the game.
- Provide an API for registering custom objectives, rewards, requirements, and conditions.
- Keep quest content separate from the framework.

## Publishing Approach

The published mod will contain the framework only. It will not bundle a playable quest line by default. Modpack authors and other mods can provide their own chapters and quests.

Example and test data may be included in development resources, but should not load as normal player-facing content in release builds.

## Documentation

| Document | Contents |
| --- | --- |
| [Features](docs/FEATURES.md) | Quest book, interface, trees, and quest behavior |
| [Quest format](docs/QUEST_FORMAT.md) | Proposed JSON structure and field definitions |
| [Content types](docs/CONTENT_TYPES.md) | Built-in objectives and rewards |
| [Requirements and conditions](docs/REQUIREMENTS_AND_CONDITIONS.md) | Unlocking rules, runtime conditions, and logic |
| [Progress and configuration](docs/PROGRESS_AND_CONFIGURATION.md) | Persistence, synchronization, and settings |
| [Commands](docs/COMMANDS.md) | Planned administration and development tools |
| [Roadmap](ROADMAP.md) | Initial release scope and later candidates |
| [Example quest](examples/quests/obtain_diamonds.json) | Example JSON quest definition |

## Core Terminology

| Term | Meaning                                                              |
| --- |----------------------------------------------------------------------|
| Chapter | A rooted directed acyclic graph of quests                            |
| Quest | A unit of progression containing objectives and rewards              |
| Objective | A trackable action or target that contributes to quest completion    |
| Reward | Something granted after a quest is completed and claimed             |
| Requirement | A prerequisite that controls whether a quest can unlock or start     |
| Condition | A contextual rule that controls visibility, activity, completion, or repetition |

## Example Identifier

All content should use namespaced identifiers:

```text
namespace:path
```

For example:

```text
example:obtain_diamonds
```

