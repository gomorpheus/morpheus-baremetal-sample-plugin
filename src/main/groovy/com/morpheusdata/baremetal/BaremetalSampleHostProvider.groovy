package com.morpheusdata.baremetal

import com.morpheusdata.PrepareHostResponse
import com.morpheusdata.core.AbstractProvisionProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.providers.HostProvisionProvider
import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.ComputeTypeLayout
import com.morpheusdata.model.HostType
import com.morpheusdata.model.Icon
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.VirtualImage
import com.morpheusdata.model.VirtualImageType
import com.morpheusdata.model.provisioning.HostRequest
import com.morpheusdata.response.ProvisionResponse
import com.morpheusdata.response.ServiceResponse
import groovy.util.logging.Slf4j

@Slf4j
class BaremetalSampleHostProvider extends AbstractProvisionProvider implements HostProvisionProvider, HostProvisionProvider.finalizeHostFacet {

	Plugin plugin
	MorpheusContext morpheusContext

	public static final String PROVIDER_NAME = 'Baremetal Sample'
	public static final String PROVIDER_CODE = 'baremetal-sample-compute-provider'

	BaremetalSampleHostProvider(Plugin plugin, MorpheusContext morpheusContext) {
		super()
		this.plugin = plugin
		this.morpheusContext = morpheusContext
	}

	@Override
	ServiceResponse stopServer(ComputeServer computeServer) {
		return null
	}

	@Override
	ServiceResponse startServer(ComputeServer computeServer) {
		return null
	}

	@Override
	MorpheusContext getMorpheus() {
		return morpheusContext
	}

	@Override
	Plugin getPlugin() {
		return plugin
	}

	@Override
	String getCode() {
		return PROVIDER_CODE
	}

	@Override
	String getName() {
		return PROVIDER_NAME
	}

	@Override
	Icon getCircularIcon() {
		return new Icon(path:'morpheus.svg', darkPath:'morpheus.svg')
	}

	@Override
	Collection<OptionType> getOptionTypes() {
		return new ArrayList<OptionType>()
	}

	@Override
	String serverType() {
		return "unmanaged"
	}

	@Override
	Boolean requiresVirtualImage() {
		return false
	}

	@Override
	Boolean supportsAgent() {
		return false
	}

	@Override
	Boolean createDefaultInstanceType() {
		return false
	}

	@Override
	HostType getHostType() {
		return HostType.bareMetal
	}

	@Override
	Collection<VirtualImage> getVirtualImages() {
		return new ArrayList<VirtualImage>()
	}

	@Override
	Collection<VirtualImageType> getVirtualImageTypes() {
		return new ArrayList<VirtualImageType>()
	}

	@Override
	Collection<ComputeTypeLayout> getComputeTypeLayouts() {
		return new ArrayList<ComputeTypeLayout>()
	}

	@Override
	ServiceResponse validateHost(ComputeServer computeServer, Map map) {
		log.info("Validating Host")
		return ServiceResponse.success()
	}

	@Override
	ServiceResponse<PrepareHostResponse> prepareHost(ComputeServer computeServer, HostRequest hostRequest, Map map) {
		log.info("Preparing Host")
		PrepareHostResponse prepareHostResponse = new PrepareHostResponse()
		prepareHostResponse.computeServer = computeServer

		return ServiceResponse.success(prepareHostResponse)
	}

	@Override
	ServiceResponse<ProvisionResponse> runHost(ComputeServer computeServer, HostRequest hostRequest, Map map) {
		log.info("Running Host")
		ProvisionResponse provisionResponse = new ProvisionResponse()
		provisionResponse.success = true

		return ServiceResponse.success(provisionResponse)
	}

	@Override
	ServiceResponse<ProvisionResponse> waitForHost(ComputeServer server) {
		ProvisionResponse provisionResponse = new ProvisionResponse()
		provisionResponse.success = true

		return ServiceResponse.success(provisionResponse)
	}

	@Override
	ServiceResponse finalizeHost(ComputeServer computeServer) {
		log.info("Finalizing Host")
		computeServer.status = "available"
		morpheus.async.computeServer.save(computeServer).subscribe()

		return ServiceResponse.success()
	}
}
