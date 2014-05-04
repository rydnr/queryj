Feature: G_CLUB_TYPE_METADATA code compiles

  Scenario Outline: G_CLUB_TYPE_METADATA-specific templates compile

    Given the following tables:
    |    table     | parent table | decorated | relationship |   static    |
    | G_CLUB_TYPE_METADATA |  | false | false |  |

    And the following columns:
    |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
    | G_CLUB_TYPE_METADATA | G_CLUB_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_CLUB_TYPE_METADATA | G_CLUB_TYPE_METADATA_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_CLUB_TYPE_METADATA | VALUE | VARCHAR2 | false | false | false |  |  | false | 150 | 0 |

    And the following queries:
      |       id      |     name      |     dao      |  type  | matches  |                        value                           | validate |
      | club.type.md.count-club-type-with-metadata-by-product-type | count-club-type-with-metadata-by-product-type | g_club_type_metadata | select | multiple |  SELECT t.* FROM g_club_type_metadata m, g_club_types t WHERE m.g_club_type_id IN ( SELECT g_club_type_id FROM g_club_metadefinition CONNECT BY PRIOR g_club_metadef_id=g_parent_id START WITH g_parent_id IS NULL AND g_product_type_id=?) AND m.g_club_type_metadata_type_id=? AND m.value =? AND t.g_club_type_id=m.g_club_type_id  | true |
      | club.type.md.find-club-type-metadata-by-product-type | find-club-type-metadata-by-product-type | g_club_type_metadata | select | multiple |  SELECT ctm.* FROM g_club_type_metadata ctm, g_club_metadefinition cm WHERE cm.g_club_type_id = ctm.g_club_type_id AND cm.g_product_type_id = ? AND ctm.g_club_type_metadata_type_id = ?  | true |
      | club.type.md.find-club-type-metadata-by-product-type-and-cycle | find-club-type-metadata-by-product-type-and-cycle | g_club_type_metadata | select | multiple |  SELECT ctm.* FROM g_club_type_metadata ctm, g_club_metadefinition cm, g_cycles c WHERE cm.g_club_type_id = ctm.g_club_type_id AND cm.g_product_type_id = ? AND ctm.g_club_type_metadata_type_id = ? and c.g_club_type_id=ctm.g_club_type_id and c.g_cycle_id = ?  | true |
      | find-by-club-type-id-and-metadata-type-id | find-by-club-type-id-and-metadata-type-id | g_club_type_metadata | select | multiple |  select * from g_club_type_metadata where g_club_type_metadata_type_id = ? and g_club_type_id = ?  | true |

    And the following query parameters:
      |            id            |     sql       | index |  type  |     name    | validation-value |
      | product-type-id.parameter-1 | club.type.md.count-club-type-with-metadata-by-product-type | 1 | long | productTypeId | 1 |
      | club.type.md.club_type_md_type_id.parameter-2 | club.type.md.count-club-type-with-metadata-by-product-type | 2 | long | club_type_md_type_id | 22 |
      | club.type.md.club_type_md_value.parameter-3 | club.type.md.count-club-type-with-metadata-by-product-type | 3 | String | club_type_md_value | true |
      | product-type-id.parameter-1 | club.type.md.find-club-type-metadata-by-product-type | 1 | long | productTypeId | 1 |
      | club.type.md.club_type_md_type_id.parameter-2 | club.type.md.find-club-type-metadata-by-product-type | 2 | long | club_type_md_type_id | 22 |
      | product-type-id.parameter-1 | club.type.md.find-club-type-metadata-by-product-type-and-cycle | 1 | long | productTypeId | 1 |
      | club.type.md.club_type_md_type_id.parameter-2 | club.type.md.find-club-type-metadata-by-product-type-and-cycle | 2 | long | club_type_md_type_id | 22 |
      | club.type.md.cycle_id.parameter-3 | club.type.md.find-club-type-metadata-by-product-type-and-cycle | 3 | long | cycle_id | 37 |
      | club-type-metadata-id.parameter-1 | find-by-club-type-id-and-metadata-type-id | 1 | long | clubTypeMetadataId | 1 |
      | club-type-metadata-id.parameter-2 | find-by-club-type-id-and-metadata-type-id | 2 | long | clubTypeId | 1 |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      | template | output |
      | DAO | OracleGClubTypeMetadataDAO.java |
