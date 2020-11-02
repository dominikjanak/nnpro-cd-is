# ISCD - Spáva mimořádností

## Základní konfigurace a přístup
Definuje stručný popis správné konfigurace pro zahájení lokálního vývoje.
### Připojení k lokální databázi
Lokální konfigurace je přednastavená. Stačí pouze v Idee změnit nastavevní konpilace 'Edit Configuration...' (vpravo nahoře, vedle tlačítka startu) a do 'Active profiles' napsat 'locale'. Následně se při lokální kompilaci bude provádět konfigurace ze souboru 'application-local.properties'.
### Přístup do lokální H2 databáze při běhu
Na adrese [app-cd-is.herokuapp.com](https://app-cd-is.herokuapp.com/) je definován odkaz na online konzoli, která se nachází na adrese '/h2-console'.
- **User:** sa
- **Pass:** password

## Externí entity - CRUD
- Railroad - done
- InterventionType - done
- AttackedSubject - done

## Interní entity - CRUD
- Area - fixed R
- Carriage - done
- Damage
- DamageType - done
- FireBridgeUnit
- FireIncident
- Incident
- PremiseIncident
- Region - fixed R
- Report - **partial**
- Role - done
- SecurityIncident
- User

## Vnitřní implementace
Stručný popis jednotlivých implementovaných sekcí

### Přhlášení
Ve výchozím stavu je předdefinován uživatel:
- **User:** admin
- **Pass:** password

### Identifikace uživatele
Vnitřní řešení zatím předpokládá použití eživatelského jména jako hlavního identifikátoru uživatele. Převedení emailu na tuto pozic bude provedeno až po vyřazení funkčnosti uživatelského jména.

### Registrace
Je podmíněna následným schválením uživatele administrátorem. **Řízení kontroly aktivovaného uživatele doposud není implementováno.**

### Žádost o obnovu hesla
Žádost je odelána po zadání uživatelského jména. **Je třeba posoudit:** odeslání emailu pro vytvoření žádosti potvrzení o obnovu hesla administrátorem.

## TODO
- Dodělat endpointy pro změnu osobních údajů a hesla. (Dominik)
- **Návrh šablony systému v Bootstrap 4.5 za pomocí FontAwesome**
- Dodělat aplikaci
- Generování reportů
- CronJob (externí - heroku se uspává nebo interní?)