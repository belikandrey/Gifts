explain select tag.id, tag.name
        from tag
                 join certificate_tag ct on tag.id = ct.tag_id
                 join certificate c on c.id = ct.certificate_id
                 join order_certificate oc on c.id = oc.certificate_id
                 join user_order uo on oc.order_id = uo.id
        where user_id = (select user.id
                         from user
                                  join user_order uo on user.id = uo.user_id
                         group by (user_id)
                         order by MAX(price) desc
                         limit 1)
        group by (tag.id)
        order by count(tag_id) desc
        limit 1;