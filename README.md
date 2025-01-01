# AreaShop PlaceholderAPI Extension

This is a lightweight plugin designed for Minecraft Java servers running Spigot, Bukkit, or Paper. It extends the functionality of [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) for [AreaShop](https://www.spigotmc.org/resources/areashop.2991/), enabling players to use placeholders that provide detailed information about regions managed by AreaShop directly in-game.

## Features

- Supports various placeholders for regions in AreaShop.
- Provides both numerical and list-based placeholders.
- Allows players to query details about available, rented, or owned regions.

## Placeholders

### General Information
- **`%areashop_num_regions%`**: Total number of regions.
- **`%areashop_num_regions_for_rent%`**: Number of regions available for rent.
- **`%areashop_num_regions_for_sale%`**: Number of regions available for sale.
- **`%areashop_num_regions_available%`**: Total number of regions available for rent or sale.
- **`%areashop_num_regions_bought%`**: Number of regions currently owned.
- **`%areashop_num_regions_rented%`**: Number of regions currently rented.
- **`%areashop_num_regions_occupied%`**: Total number of regions with an owner or tenant.

### Lists
- **`%areashop_list_regions%`**: List of all regions.
- **`%areashop_list_regions_for_rent%`**: List of regions available for rent.
- **`%areashop_list_regions_for_sale%`**: List of regions available for sale.
- **`%areashop_list_regions_bought%`**: List of owned regions.
- **`%areashop_list_regions_rented%`**: List of rented regions.
- **`%areashop_list_regions_occupied%`**: List of all occupied regions.

### Player-Specific Information
- **`%areashop_num_regions_bought_own%`**: Number of regions the player owns.
- **`%areashop_num_regions_rented_own%`**: Number of regions the player rents.
- **`%areashop_num_regions_occupied_own%`**: Total number of regions the player owns or rents.
- **`%areashop_list_regions_bought_own%`**: List of regions owned by the player.
- **`%areashop_list_regions_rented_own%`**: List of regions rented by the player.
- **`%areashop_list_regions_occupied_own%`**: List of all regions the player owns or rents.

### Financial Information
- **`%areashop_num_price_owed%`**: Total amount owed by the player for all rented regions.
- **`%areashop_num_price_paid%`**: Total amount paid by the player for all owned regions.
- **`%areashop_list_price_owed%`**: List of rent costs for the player's rented regions.
- **`%areashop_list_price_paid%`**: List of prices paid for the player's owned regions.
- **`%areashop_list_price%`**: List of all costs (rent and purchase) for the player's regions.

## Contributions

Contributions are welcome! If you have suggestions, encounter issues, or want to request new placeholders, feel free to open an issue or submit a pull request.
