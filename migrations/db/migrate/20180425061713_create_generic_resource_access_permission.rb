class CreateGenericResourceAccessPermission < ActiveRecord::Migration[5.1]
  def change
    create_table :generic_resource_access_permissions do |t|
      t.references :resource, polymorphic: true, index: {name: "gereacpe_re"}
      t.references :access_granted_to, polymorphic: true, index: {name: "gereacpe_acgrto"}
      t.string :access_reason_code
      t.references :access_originating_from, polymorphic: true, index: {name: "gereacpe_acorfr"}
      t.timestamps
    end
  end
end
