Feature: G_TAX_PRODUCTS code compiles

  Scenario Outline: G_TAX_PRODUCTS-specific templates compile

    Given the following tables:
    |    table     | parent table | decorated | relationship |   static    |
    | G_TAX_PRODUCTS |  | false | false |  |

    And the following columns:
    |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
    | G_TAX_PRODUCTS | G_PRODUCT_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_TAX_PRODUCTS | G_TAX_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_TAX_PRODUCTS | AMOUNT | NUMBER | false | false | false |  |  | false | 22 | 10 |
    | G_TAX_PRODUCTS | TAX_AMOUNT | NUMBER | false | false | false |  |  | false | 22 | 10 |
    | G_TAX_PRODUCTS | LAST_MODIFIED | DATE | false | false | true |  |  | false | 7 | 0 |
    | G_TAX_PRODUCTS | CREATION_DATE | DATE | false | false | true |  |  | false | 7 | 0 |

    And the following foreign keys:
    | source table | source columns | target table | allows null |
    | G_TAX_PRODUCTS | G_PRODUCT_ID | G_PRODUCTS | false |
    | G_TAX_PRODUCTS | G_TAX_ID | G_TAXES | false |

    And the following queries:
      |       id      |     name      |     dao      |  type  | matches  |                        value                           | validate |
      | find-by-club-product-and-draw | findByClubProductAndDraw | g_tax_products | select | multiple |  select tp.g_tax_id, tp.g_product_id, t.amount, tp.tax_amount, pc.g_prize_category_id, ct.g_club_type_id, ctm.value shares from g_shares s, g_purchases pu, g_bets b, g_prizes pr, g_taxes t, g_tax_products tp, g_prize_quotas pq, g_prize_categories pc, g_clubs c, g_club_types ct, g_club_type_metadata ctm where  s.g_product_id = ? and t.g_tax_type_id = ? and s.g_club_id = pu.g_bettor_id and pu.g_bettor_type_id = 2 and pu.g_purchase_id = b.g_purchase_id and pr.g_winner_id = b.g_bet_id and pr.g_draw_id = ? and pr.g_prize_id = t.g_prize_id and tp.g_tax_id = t.g_tax_id and tp.g_product_id = s.g_product_id and pq.g_prize_quota_id = pr.g_prize_quota_id and pc.g_prize_category_id = pq.g_prize_category_id and c.g_club_id = s.g_club_id and ct.g_club_type_id = c.g_club_type_id and ct.g_club_type_id = ctm.g_club_type_id and ctm.g_club_type_metadata_type_id = 2 order by ct.g_club_type_id, pc.g_prize_category_id  | true |
      | find-by-group-product-and-draw | findByGroupProductAndDraw | g_tax_products | select | multiple |  select tp.g_tax_id, tp.g_product_id, t.amount, tp.tax_amount, pc.g_prize_category_id from g_tax_products tp, g_taxes t, g_prizes p, g_prize_quotas pq, g_prize_categories pc, g_bets b, g_purchases pu where tp.g_tax_id = t.g_tax_id and tp.g_product_id = ? and t.g_tax_type_id = ? and p.g_prize_id = t.g_prize_id and p.g_draw_id = ? and pq.g_prize_quota_id = p.g_prize_quota_id and pc.g_prize_category_id = pq.g_prize_category_id and p.g_winner_id = b.g_bet_id and p.g_winner_type_id = 1 and b.g_purchase_id = pu.g_purchase_id and pu.g_bettor_type_id = 1 and pu.g_product_id <> tp.g_product_id  | true |
      | find-by-ind-product-and-draw | findByIndProductAndDraw | g_tax_products | select | multiple |  select tp.g_tax_id, tp.g_product_id, t.amount, tp.tax_amount, pc.g_prize_category_id from g_tax_products tp, g_taxes t, g_prizes p, g_prize_quotas pq, g_prize_categories pc, g_bets b, g_purchases pu where tp.g_tax_id = t.g_tax_id and tp.g_product_id = ? and t.g_tax_type_id = ? and p.g_prize_id = t.g_prize_id and p.g_draw_id = ? and pq.g_prize_quota_id = p.g_prize_quota_id and pc.g_prize_category_id = pq.g_prize_category_id and p.g_winner_id = b.g_bet_id and p.g_winner_type_id = 1 and b.g_purchase_id = pu.g_purchase_id and pu.g_bettor_type_id = 1 and pu.g_product_id = tp.g_product_id  | true |
      | find-by-product | findByProduct | g_tax_products | select | multiple |  select tp.g_tax_id, tp.g_product_id, tp.amount, tp.tax_amount, tp.last_modified, tp.creation_date from g_tax_products tp, g_taxes t where  tp.g_tax_id = t.g_tax_id and tp.g_product_id = ? and t.g_tax_type_id = ?  | true |

    And the following query parameters:
      |            id            |     sql       | index |  type  |     name    | validation-value |
      | tax_products.product_id.parameter-1 | find-by-club-product-and-draw | 1 | long | productId | 1 |
      | tax_products.tax_type_id.parameter-2 | find-by-club-product-and-draw | 1 | long | taxTypeId | 1 |
      | tax_products.draw_id.parameter-3 | find-by-club-product-and-draw | 3 | long | drawId | 1 |
      | tax_products.product_id.parameter-1 | find-by-group-product-and-draw | 1 | long | productId | 1 |
      | tax_products.tax_type_id.parameter-2 | find-by-group-product-and-draw | 1 | long | taxTypeId | 1 |
      | tax_products.draw_id.parameter-3 | find-by-group-product-and-draw | 3 | long | drawId | 1 |
      | tax_products.product_id.parameter-1 | find-by-ind-product-and-draw | 1 | long | productId | 1 |
      | tax_products.tax_type_id.parameter-2 | find-by-ind-product-and-draw | 1 | long | taxTypeId | 1 |
      | tax_products.draw_id.parameter-3 | find-by-ind-product-and-draw | 3 | long | drawId | 1 |
      | tax_products.product_id.parameter-1 | find-by-product | 1 | long | productId | 1 |
      | tax_products.tax_type_id.parameter-2 | find-by-product | 1 | long | taxTypeId | 1 |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      |       template            |               output               |
      | ResultSetExtractor        | GTaxProductResultSetExtractor.java |
      | ValueObject               | GTaxProduct.java                   |
      | BaseValueObject           | AbstractGTaxProduct.java           |
      | ValueObjectImpl           | GTaxProductImpl.java               |
      | ValueObjectFactory        | GTaxProductFactory.java            |
      | AttributesStatementSetter | GTaxProductStatementSetter.java    |
      | PkStatementSetter         | GTaxProductPkStatementSetter.java  |
      | BaseDAO                   | GTaxProductDAO.java                |
      | DAOFactoryHelper          | GTaxProductDAOFactoryHelper.java   |
      | DAOFactory | OracleGTaxProductDAOFactory.java |
      | BaseDAOFactory | GTaxProductDAOFactory.java |
#      | DAO | OracleGTaxProductDAO.java |
