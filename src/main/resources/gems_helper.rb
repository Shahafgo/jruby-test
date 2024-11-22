require 'rubygems'
require 'rubygems/package'

module Gems
  class Helper
    def name_version_platform(gem_file_path)
      spec = Gem::Package.new(gem_file_path).spec
      spec.name + ',' + spec.version.to_s + ',' + (spec.platform.nil? ? 'unknown' : spec.platform.to_s)
    end
  end
end
Gems::Helper